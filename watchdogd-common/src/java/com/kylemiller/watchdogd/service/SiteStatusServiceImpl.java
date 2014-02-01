package com.kylemiller.watchdogd.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.SiteStatus;
import com.kylemiller.watchdogd.model.dao.SiteStatusDAO;
import com.kylemiller.watchdogd.service.notification.NotificationService;

@Service("siteStatusService")
public class SiteStatusServiceImpl implements SiteStatusService {

	@Resource
	private SiteStatusDAO siteStatusDAO;
	@Resource
	private NotificationService notificationService;
	
	public void toggleStatus(SiteReport report) {
		
		SiteStatus status = report.getSite().getStatus();
		
		status.setLastConnected(new Date());
		status.setValue(report.getValue()) //TODO: (Kyle) test and fix this
		
		if (!StringUtils.equalsIgnoreCase(status.getStatus(), report.getType()) || null == status.getNotified()) {
			getNotificationService().notify(report);
			status.setNotified(new Date());
		} 
		
		status.setStatus(report.getType());
		getSiteStatusDAO().update(status);
	}

	public SiteStatusDAO getSiteStatusDAO() {
		return siteStatusDAO;
	}

	public void setSiteStatusDAO(SiteStatusDAO siteStatusDAO) {
		this.siteStatusDAO = siteStatusDAO;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
}
