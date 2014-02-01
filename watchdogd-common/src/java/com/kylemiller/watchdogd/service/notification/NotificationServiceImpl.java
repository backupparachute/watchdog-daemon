package com.kylemiller.watchdogd.service.notification;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kylemiller.watchdogd.model.Contact;
import com.kylemiller.watchdogd.model.SiteNotification;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.dao.SiteNotificationDAO;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {
	
	private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	@Resource
	private SiteNotificationDAO siteNotificationDAO;
	
	public void notify(SiteReport report) {
		
		for (Contact contact : report.getSite().getContacts()) {
			
			log.debug(String.format("creating notification for contact: %s", contact.getName()));
			SiteNotification notify = new SiteNotification();
			notify.setSiteReport(report);
			notify.setSite(report.getSite());
			notify.setContact(contact.getValue());
			notify.setName(contact.getName());
			notify.setType(contact.getType());
			
			generateJSON(notify, contact);
			
			getSiteNotificationDAO().save(notify);
			
			log.debug("finished creating contact...");
		}
		
		
	}

	private void generateJSON(SiteNotification notify, Contact contact) {
		JSONObject json = new JSONObject();
		
		json.put("site", notify.getSite().toJSON());
		json.put("report", notify.getSiteReport().toJSON());
		json.put("contact", contact.toJSON());
		
		notify.setJson(json.toJSONString());
	}

	public SiteNotificationDAO getSiteNotificationDAO() {
		return siteNotificationDAO;
	}

	public void setSiteNotificationDAO(SiteNotificationDAO siteNotificationDAO) {
		this.siteNotificationDAO = siteNotificationDAO;
	}
	
}
