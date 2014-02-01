package com.kylemiller.watchdogd.web.webhooks.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PingWebhookHandler implements WebhookHandler<Object> {
	
	private final Logger log = LoggerFactory.getLogger(PingWebhookHandler.class);
	
	@Override
	public boolean handle(Object object) {
		log.debug("PING webhook received...");
		return true;
	}

}
