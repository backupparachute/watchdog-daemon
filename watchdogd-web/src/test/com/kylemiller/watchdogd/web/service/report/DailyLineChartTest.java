package com.kylemiller.watchdogd.web.service.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;


public class DailyLineChartTest {

	@Test
	public void testLineChart() {
		
		List<Map> data = generateRowData();
		
		List<Integer> ld = new ArrayList();
		List<String> labels = new ArrayList();
		
		boolean up = false;
		for(Map map : data) {
			String s = null;
			if (StringUtils.equals("SUCCESS", s = MapUtils.getString(map, "status")) && !up) {
				//ld.add(75);
			} else {
				ld.add(50);
				labels.add(DateFormatUtils.format((Date) MapUtils.getObject(map, "date"), "kk:mm"));
			}
			
		}
		
        Line line = Plots.newLine(Data.newData(ld));
        line.addShapeMarkers(Shape.CIRCLE,Color.BLACK,4);
        line.setFillAreaColor(Color.newColor("D4F3C8"));
        
        LineChart chart = GCharts.newLineChart(line);
        chart.setSize(1000, 100);
        //chart.setTitle("Web Traffic|(in billions of hits)", WHITE, 14);
        //chart.addHorizontalRangeMarker(40, 60, Color.newColor(RED, 30));
        //chart.addVerticalRangeMarker(70, 90, Color.newColor(GREEN, 30));
        chart.setGrid(2, 100, 1, 5);
        
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(labels));
        
        System.out.println(chart.toURLString());
 		
		
	}

	private List<Map> generateRowData() {
		List<Map> data = new ArrayList();
		
	 	for (int i = 0; i<288; i++) {
			Map row = new HashMap();
			row.put("date", DateUtils.addMinutes(new Date(), i));
			row.put("status", ((i%10)==0 ? "FAILURE" : "SUCCESS"));
			data.add(row);
	 	}
		
		return data;
	}
}
