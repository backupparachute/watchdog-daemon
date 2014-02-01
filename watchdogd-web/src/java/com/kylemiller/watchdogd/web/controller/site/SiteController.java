package com.kylemiller.watchdogd.web.controller.site;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.model.dao.SiteNotificationDAO;
import com.kylemiller.watchdogd.model.dao.SiteReportDAO;
import com.kylemiller.watchdogd.util.DateHelper;
import com.kylemiller.watchdogd.util.SimplePaginatedList;
import com.kylemiller.watchdogd.web.service.report.ReportService;
import com.kylemiller.watchdogd.web.service.sites.SiteService;


@Controller
public class SiteController {
	
	@Resource
	private SiteService siteService;
	@Resource
	private SiteDAO siteDAO;
	@Resource
	private ReportService reportService;
	@Resource
	SiteReportDAO siteReportDAO;
	@Resource
	SiteNotificationDAO siteNotificationDAO;
	@Resource
	DateHelper dateHelper;
	
	@RequestMapping(value="/site/{id}",method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Integer id) throws Exception {
		
		Map map = getSiteService().load(id);
		
		ModelAndView mv = new ModelAndView();
		
		String responseTimeUrl = getReportService().generateResponseTimeReportUrl(id, DateUtils.addDays(new Date(), -7));
		mv.addObject("responseTimeUrl", responseTimeUrl);
		
		mv.addObject("customValueUrl", getReportService().generateCustomValueReportUrl(id, DateUtils.addDays(new Date(), -7)));
		
		Date date = getDateHelper().daysBeforeMidnight(-7);
		
		String uptimeUrl = getReportService().generateUptimeReportUrl(id, date);
		
		BigDecimal percent = getReportService().generateUptimePercent(id, date);
		
		mv.addObject("uptimeUrl", uptimeUrl);
		mv.addObject("percent", percent);
		
		date = DateUtils.addDays(new Date(), -1);
		
		SimplePaginatedList data = getSiteReportDAO().findSiteReportsPaginated(1, id, date);
		
		mv.addObject("date", date);
		mv.addObject("siteReports", data);
		mv.addObject("_days", -1);
		mv.addObject("notifications", getSiteNotificationDAO().findAllForSite(id, date, 25));
		mv.addObject("_site", id);
		
		Site site = (Site) map.get("site");
		mv.setViewName(String.format("site/site-details"));
		mv.addAllObjects(map);
		
		return mv;
	}
	
	@ModelAttribute
	public Site createModel(@PathVariable("id") Integer id) {
		
		 Site site = getSiteDAO().findById(id);
		return site;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public SiteReportDAO getSiteReportDAO() {
		return siteReportDAO;
	}

	public void setSiteReportDAO(SiteReportDAO siteReportDAO) {
		this.siteReportDAO = siteReportDAO;
	}

	public DateHelper getDateHelper() {
		return dateHelper;
	}

	public void setDateHelper(DateHelper dateHelper) {
		this.dateHelper = dateHelper;
	}

	public SiteNotificationDAO getSiteNotificationDAO() {
		return siteNotificationDAO;
	}

	public void setSiteNotificationDAO(SiteNotificationDAO siteNotificationDAO) {
		this.siteNotificationDAO = siteNotificationDAO;
	}
	
}
