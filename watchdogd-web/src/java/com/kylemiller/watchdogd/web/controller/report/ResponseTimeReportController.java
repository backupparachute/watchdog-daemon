package com.kylemiller.watchdogd.web.controller.report;

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
public class ResponseTimeReportController {
	private final Logger log = LoggerFactory.getLogger(ResponseTimeReportController.class);
	
	@Resource
	private ReportService reportService;
	
	@RequestMapping(value="/site/{id}/report/response",method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Integer id) throws Exception {
		
		Map model = new HashMap();
		
		String url = getReportService().generateResponseTimeReportUrl(id, DateUtils.addDays(new Date(), -7));
		
		model.put("reportUrl", url);
		
		return new ModelAndView("report/responsetime", model);
	}
	public ReportService getReportService() {
		return reportService;
	}
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

}
