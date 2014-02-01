package com.kylemiller.watchdogd.web.controller.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.web.service.report.ReportService;

@Controller
public class UptimeReportController {
	private final Logger log = LoggerFactory.getLogger(UptimeReportController.class);
	
	@Resource
	private ReportService reportService;
	
	@RequestMapping(value="/site/{id}/report/uptime",method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Integer id) throws Exception {
		Map model = new HashMap();
		
		Date date =  DateUtils.parseDate("1970-01-01", new String[] {"yyyy-dd-MM"});
		
		String url = getReportService().generateUptimeReportUrl(id, date);
		
		BigDecimal percent = getReportService().generateUptimePercent(id, date);
		
		model.put("reportUrl", url);
		model.put("percent", percent);
		
		return new ModelAndView("report/uptime", model);
	}
	public ReportService getReportService() {
		return reportService;
	}
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

}
