package com.kylemiller.watchdogd.web.service.report;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.dao.SiteReportDAO;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
	@Resource
	private SiteReportDAO siteReportDAO;

	public String generateCustomValueReportUrl(Integer id, Date date) {
		List<Map> data = getSiteReportDAO().findSiteValueByHour(id, date);
		return generateAvgByHourReportUrl(id, date, "customValue", data);
	}
	@SuppressWarnings("unchecked")
	public String generateResponseTimeReportUrl(Integer id, Date date) {
		List<Map> data = getSiteReportDAO().findSiteResponseTimeByHour(id, date);
		return generateAvgByHourReportUrl(id, date, "responseTime", data);
	}
	
	public String generateAvgByHourReportUrl(Integer id, Date date, String key, List<Map> data) {
		
		
		List<Integer> hours = new ArrayList<Integer>();
		List<Integer> resps = new ArrayList<Integer>();
		
		int i = 0;
		for (Map<String, Object> map : data) {
			
			Integer hour = MapUtils.getInteger(map, "hour");
			
			if (i == hour) {
				hours.add(i);
				resps.add(MapUtils.getInteger(map, key));
				i++;
			} else {
				i = zeroFill(i, hour, hours, resps);
			}
		}
		
		if (i < 24) {
			i = zeroFill(i, 23, hours, resps);
		}
		
		log.debug(key+" raw data: "+ReflectionToStringBuilder.toString(resps.toArray()));
		
		if (CollectionUtils.isEmpty(resps)) return "";
		
		Integer max = Collections.max(resps); //TODO: (Kyle) custom value can be null and breaks this
		//Integer min = Collections.min(resps);
		
		if (0 >= max) return "";
		
		double mcalc = max*1.05;
		Long interval = determineInterval(mcalc);
		
		Data d = DataUtil.scaleWithinRange(0, mcalc, resps); 
		
		Line line = Plots.newLine(d, Color.newColor(Color.OLIVEDRAB,80));
		line.setFillAreaColor(Color.newColor(Color.OLIVEDRAB, 40));
		line.addShapeMarkers(Shape.CIRCLE, Color.OLIVEDRAB, 4);
		LineChart chart = GCharts.newLineChart(line);
		chart.setSize(400, 200);
		chart.setGrid(50, 10, 2, 5);
		chart.addXAxisLabels(AxisLabelsFactory.newNumericAxisLabels(hours));
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, mcalc, interval));
		log.debug(key+" report date: "+date);
		log.debug(key+" report max amount: "+max);
		log.debug("##### "+key+" report url: "+chart.toURLString());
		return chart.toURLForHTML();
	}
	protected Long determineInterval(double mcalc) {
//		return Math.round(mcalc / 200)*100;
		return Math.round(mcalc / 7);
	}

	private Integer zeroFill(Integer i, Integer hour, List<Integer> hours, List<Integer> resps) {
		int j = i;
		for (; j <= hour; j++) {
			hours.add(j);
			resps.add(0);
		}
		
		return j;
	}

	@SuppressWarnings("unchecked")
	public String generateUptimeReportUrl(Integer id, Date date) {
		
		List<Map> data = getSiteReportDAO().findSiteStatusCountPerDay(id, date);
		
		Map<String, Map> sorted = sortData(data);
		
		List<Integer> success = new ArrayList();
		List<Integer> failure = new ArrayList();
		List<String> axis = new ArrayList();
		
		scrubDataPercentages(sorted, success, failure, axis);
		
		if (null == success || null == failure || success.isEmpty() || failure.isEmpty()) return "";

		return generateUptimeChartUri(success, failure, axis);
	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal generateUptimePercent(Integer id, Date date) {
		List<Map> data = getSiteReportDAO().findSiteReportsByType(id, date);
		
		Long total = calculateTotal(data);
		
		BigDecimal count = new BigDecimal(findUpCount(data));
		
		if (total > 0) {
			BigDecimal percent = count.divide(new BigDecimal(total), 4, RoundingMode.HALF_UP);
			percent = percent.multiply(new BigDecimal(100), new MathContext(4, RoundingMode.HALF_UP));
			return percent;
		}
		
		return new BigDecimal(100);
	}
	
	private Integer findUpCount(List<Map> data) {
		for(Map map : data) {
			if (StringUtils.equals("UP", MapUtils.getString(map, "status"))) return MapUtils.getInteger(map, "count");
		}
		return 0;
	}

	private Long calculateTotal(List<Map> data) {
		Long total = 0l;
		for(Map map : data) {
			Long l = (Long) map.get("count");
			total = total + l;
		}
		return total;
	}

	private String generateUptimeChartUri(List<Integer> success, List<Integer> failure, List<String> axis) {
		BarChartPlot sp = Plots.newBarChartPlot(Data.newData(success), Color.newColor(Color.OLIVEDRAB,80), "Up");
		BarChartPlot fp = Plots.newBarChartPlot(Data.newData(failure),  Color.newColor(Color.SALMON,80), "Down");
		
		BarChart chart = GCharts.newBarChart(sp, fp);
		
		chart.setSize(400, 200);
		chart.setDataStacked(true);
		chart.setSpaceBetweenGroupsOfBars(10);
		chart.setSpaceWithinGroupsOfBars(10);
		//chart.setBarWidth(4);
//		chart.setGrid(100, 25, 2, 5);
		chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(axis));
		chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 100, 25));
		
		String uri = chart.toURLForHTML();
		return uri;
	}

	private void scrubDataPercentages(Map<String, Map> sorted, List<Integer> success, List<Integer> failure, List<String> axis) {
		for (Map.Entry<String, Map> m : sorted.entrySet()) {
			Float s = MapUtils.getFloat(m.getValue(), SiteReport.SUCCESS_TYPE, 0f);
			Float f = MapUtils.getFloat(m.getValue(), SiteReport.FAILURE_TYPE, 0f);
			
			Float total = new Float(s+f);
			
			s = (s/total)*100;
			f = (f/total)*100;
			
			Integer si = s.intValue();
			Integer fi = f.intValue();
			
			Integer ti = si + fi;
			
			//ensures that they always equal 100, favoring the smaller percent
			//so that the graph will show red if there was a problem, and not
			//round it away and give a false impression of perfect uptime.
			if (ti > 100) {
				if (si > 50) si--;
				else if (fi > 50) fi--;
			} else if (ti < 100) {
				if (si < 50) si++;
				else if (fi < 50) fi++;
			}
			
			try {
				Date d = DateUtils.parseDate(m.getKey(), new String[] {"yyyy-MM-dd"});
				
				axis.add(DateFormatUtils.format(d, "MM.dd"));
			} catch (ParseException e) {
			}
			success.add(si);
			failure.add(fi);
		}
	}

	private Map<String, Map> sortData(List<Map> data) {
		Map<String, Map> sorted = new TreeMap<String, Map>();
		
		for (Map map : data) {
			//log.debug(String.format("**** count: %s, date: %s, status: %s\n", map.get("count"), map.get("date"), map.get("status")));
			Map m = findMap(sorted, map);
			m.put(MapUtils.getString(map, "status"), MapUtils.getString(map, "count"));
			sorted.put(MapUtils.getString(map, "date"), m);
		}
		return sorted;
	}

	private Map findMap(Map<String, Map> sorted, Map map) {
		Map m = sorted.get(MapUtils.getString(map, "date"));
		if (null == m) m = new HashMap();
		return m;
	}

	public SiteReportDAO getSiteReportDAO() {
		return siteReportDAO;
	}

	public void setSiteReportDAO(SiteReportDAO siteReportDAO) {
		this.siteReportDAO = siteReportDAO;
	}
}
