package com.kylemiller.watchdogd.web.webhooks.handlers;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kylemiller.watchdogd.service.EmailService;

@Component
public class NoopWebhookHandler implements WebhookHandler<Object> {

	private final Logger log = LoggerFactory.getLogger(NoopWebhookHandler.class);
	
	@Override
	public boolean handle(Object obj) {
		log.debug("##### NOOP Webhook: %s", obj);
		return true;
	}

}
