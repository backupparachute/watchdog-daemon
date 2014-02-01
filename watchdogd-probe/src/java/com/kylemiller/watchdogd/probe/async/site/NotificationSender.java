package com.kylemiller.watchdogd.probe.async.site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.SiteNotification;
import com.kylemiller.watchdogd.model.dao.SiteNotificationDAO;
import com.kylemiller.watchdogd.service.notification.notifiers.Notifier;

public class NotificationSender implements Runnable {

	private final Logger log = LoggerFactory.getLogger(NotificationSender.class);
	private SiteNotificationDAO siteNotificationDAO;
	private Map<String, Notifier> notifiers = new HashMap();
	
	@Override
	public void run() {
		
		log.debug("searching for notifications to send...");
		List<SiteNotification> notes = getSiteNotificationDAO().findUnsent();
		log.debug(String.format("found %s notifications to send...",notes.size()));
		
		for (SiteNotification note : notes) {
			
			try {
				log.debug("finding notifier: id="+note.getId());
				Notifier notifier = getNotifiers().get(note.getType());
				
				if (null != notifier) {
					notifier.notify(note);
					note.setSuccess(true);
					note.setErrorCode(null);
					note.setErrorMessage(null);
				}
			} catch (Exception e) {
				note.setSuccess(false);
				note.setErrorMessage(e.getMessage());
				log.error("error sending notification: "+e.getMessage());
			}
			
			increment(note);
			getSiteNotificationDAO().update(note);
			
		}
		
		log.debug("finished sending notifications...");
		
	}

	private void increment(SiteNotification note) {
		Integer i = note.getAttempts();
		
		if (null == i) note.setAttempts(1);
		else note.setAttempts(note.getAttempts()+1);
	}

	public SiteNotificationDAO getSiteNotificationDAO() {
		return siteNotificationDAO;
	}

	public void setSiteNotificationDAO(SiteNotificationDAO siteNotificationDAO) {
		this.siteNotificationDAO = siteNotificationDAO;
	}

	public Map<String, Notifier> getNotifiers() {
		return notifiers;
	}

	public void setNotifiers(Map<String, Notifier> notifiers) {
		this.notifiers = notifiers;
	}

}
