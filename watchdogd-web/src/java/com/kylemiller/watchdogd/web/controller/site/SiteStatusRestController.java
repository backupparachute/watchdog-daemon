package com.kylemiller.watchdogd.web.controller.site;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.StatusCode;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.model.dao.SiteReportDAO;
import com.kylemiller.watchdogd.service.SiteStatusService;


@Controller
public class SiteStatusRestController {
	
	@Resource
	private SiteReportDAO siteReportDAO;
	@Resource
	private SiteDAO siteDAO;
	@Resource 
	private SiteStatusService siteStatusService;
	
	@RequestMapping(value="/site/{id}/status/up",method={RequestMethod.POST,RequestMethod.GET})
	protected void up(@ModelAttribute Site command, HttpServletResponse response, @RequestParam(value="response-time",required=false) String responseTime, @RequestParam(value="custom-value",required=false) String customValue) throws Exception {
		
		createRestReport(command, StatusCode.OK, SiteReport.SUCCESS_TYPE, responseTime, customValue);
		
		response.setStatus(HttpServletResponse.SC_OK);
	}

	protected void createRestReport(Site command, StatusCode code, String type,  String responseTime, String customValue) {
		SiteReport report = new SiteReport();
		report.setType(type);
		report.setResponseTime(new Long(-1));
		report.setStatusCode(code.getCode());
		report.setSite(command);
		
		if (StringUtils.isNotBlank(customValue) && StringUtils.isNumeric(customValue)) report.setValue(new Integer(customValue));
		if (StringUtils.isNotBlank(responseTime) && StringUtils.isNumeric(responseTime)) report.setResponseTime(new Long(responseTime));
		
		getSiteReportDAO().save(report);
		
		getSiteStatusService().toggleStatus(report);
	}	
		
	@RequestMapping(value="/site/{id}/status/down",method= {RequestMethod.POST,RequestMethod.GET})
	protected void down(@ModelAttribute Site command, HttpServletResponse response,  @RequestParam(value="response-time",required=false) String responseTime, @RequestParam(value="custom-value",required=false) String customValue) throws Exception {
		
		createRestReport(command, StatusCode.ERROR, SiteReport.FAILURE_TYPE, responseTime, customValue);
		
		response.setStatus(HttpServletResponse.SC_OK);
	}	
	
	
	@ModelAttribute
	public Site findModel(@PathVariable("id") String id) {
		 Site site = getSiteDAO().findById(new Integer(id));
		return site;
	}
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}

	public SiteStatusService getSiteStatusService() {
		return siteStatusService;
	}

	public void setSiteStatusService(SiteStatusService siteStatusService) {
		this.siteStatusService = siteStatusService;
	}

	public SiteReportDAO getSiteReportDAO() {
		return siteReportDAO;
	}

	public void setSiteReportDAO(SiteReportDAO siteReportDAO) {
		this.siteReportDAO = siteReportDAO;
	}
	
}
