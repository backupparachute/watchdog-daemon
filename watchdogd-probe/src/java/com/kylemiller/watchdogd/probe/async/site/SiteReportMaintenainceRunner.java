package com.kylemiller.watchdogd.probe.async.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.dao.SiteReportDAO;
import com.kylemiller.watchdogd.util.DateHelper;

public class SiteReportMaintenainceRunner implements Runnable {
	
	private final Logger log = LoggerFactory.getLogger(SiteReportMaintenainceRunner.class);
	private SiteReportDAO siteReportDAO;
	private DateHelper dateHelper;
	
	@Override
	public void run() {
		
		log.debug("deleting old site reports");
		
		getSiteReportDAO().deleteSiteReportsOlderThan(getDateHelper().daysBeforeMidnight(-8));
		
		log.debug("delete site reports finished...");
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
}
