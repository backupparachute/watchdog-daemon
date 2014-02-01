package com.kylemiller.watchdogd.probe.async.site;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.StatusCode;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.model.dao.SiteReportDAO;
import com.kylemiller.watchdogd.service.SiteStatusService;
import com.kylemiller.watchdogd.service.notification.NotificationService;

public class DownSiteMonitor implements Runnable {
	
	private final Logger log = LoggerFactory.getLogger(DownSiteMonitor.class);
	private SiteDAO siteDAO;
	private SiteReportDAO siteReportDAO;
	private NotificationService notificationService;
	private SiteStatusService siteStatusService;
	//private SubscriptionDAO subscriptionDAO;
	private Integer downSiteInterval = -6;
	
	@Override
	public void run() {
		
		log.debug("checking for down sites...");
		
			List<Site> sites = getSiteDAO().findDownSites(DateUtils.addMinutes(new Date(), getDownSiteInterval()));
			
			log.debug(String.format("found %s sites down...",  sites.size()));
			
			for (Site site : sites) {
				
				SiteReport report = new SiteReport(site);
				report.setType(report.FAILURE_TYPE);
				report.setStatusCode(StatusCode.NOT_UPDATED_TIMELY.getCode());
				report.setMessage("Site has not been updated in a timely fashion."); //TODO: (Kyle) change the message if threshold exceeded
				report.setResponseTime(new Long(-1));
				
				getSiteReportDAO().save(report);
				
				getSiteStatusService().toggleStatus(report);
				
			}
		
		log.debug("down site checked finished...");
	}
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}
	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	public SiteReportDAO getSiteReportDAO() {
		return siteReportDAO;
	}
	public void setSiteReportDAO(SiteReportDAO siteReportDAO) {
		this.siteReportDAO = siteReportDAO;
	}
	public SiteStatusService getSiteStatusService() {
		return siteStatusService;
	}
	public void setSiteStatusService(SiteStatusService siteStatusService) {
		this.siteStatusService = siteStatusService;
	}
	public Integer getDownSiteInterval() {
		return downSiteInterval;
	}
	public void setDownSiteInterval(Integer downSiteInterval) {
		this.downSiteInterval = downSiteInterval;
	}
	
}
