package com.kylemiller.watchdogd.web.controller.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.dao.SiteReportDAO;
import com.kylemiller.watchdogd.util.SimplePaginatedList;

@Controller
public class SiteReportController {
	
	private String view = "report/site-report";
	@Resource
	private SiteReportDAO siteReportDAO;

	@RequestMapping(value="/site/{siteId}/reports")
	public ModelAndView handle(@PathVariable("siteId") String site, @RequestParam(value="page",required=false) String page) throws Exception {
		return work(site, "-7", page);
	}
	
	@RequestMapping(value="/site/{siteId}/reports/{days}")
	public ModelAndView handle(@PathVariable("siteId") String site, @PathVariable("days") String days, @RequestParam(value="page",required=false) String page) throws Exception {
		return work(site,days,page);
	}
	
	public ModelAndView work( String site,String days, String page) throws Exception {
		Map model = new HashMap();
		
		Date date = DateUtils.addDays(new Date(), NumberUtils.toInt("-"+days, -7));
		
		SimplePaginatedList data = getSiteReportDAO().findSiteReportsPaginated(NumberUtils.toInt(page, 1), NumberUtils.toInt(site, -1), date);
		
		model.put("date", date);
		model.put("siteReports", data);
		model.put("_days", days);
		model.put("_site", site);
		
		return new ModelAndView(getView(), model);
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public SiteReportDAO getSiteReportDAO() {
		return siteReportDAO;
	}

	public void setSiteReportDAO(SiteReportDAO siteReportDAO) {
		this.siteReportDAO = siteReportDAO;
	}
	
}
