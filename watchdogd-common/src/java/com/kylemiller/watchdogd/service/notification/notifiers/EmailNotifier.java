package com.kylemiller.watchdogd.service.notification.notifiers;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.SiteNotification;
import com.kylemiller.watchdogd.service.EmailService;

public class EmailNotifier implements Notifier {

	private final Logger log = LoggerFactory.getLogger(EmailNotifier.class);
	private EmailService emailService;
	
	@Override
	public void notify(SiteNotification notify) {
		
		if (!notify.isContentReady()) generate(notify);
		
		email(notify);
		
	}
	
	private void email(SiteNotification notify) {
		getEmailService().send(notify.getContact(), notify.getSubject(), notify.getBody());
		log.debug("EMAIL:  message sent: ");
	}

	private void generate(SiteNotification notify) {
		StringBuilder sb = new StringBuilder();
		
		notify.setSubject(String.format("%s : %s : %s", notify.getSite().getName(), notify.getSiteReport().getType(), notify.getSiteReport().getStatusCode()));
		notify.setBody(String.format("%s [%s]\n\nStatus: %s %s", notify.getSite().getName(), notify.getSite().getUri(), notify.getSiteReport().getStatusCode(), StringUtils.defaultIfEmpty(notify.getSiteReport().getMessage(),"")));
		notify.setContentReady(true);
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
