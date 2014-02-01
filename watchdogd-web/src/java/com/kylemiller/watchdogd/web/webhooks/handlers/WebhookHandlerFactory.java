package com.kylemiller.watchdogd.web.webhooks.handlers;

public interface WebhookHandlerFactory {
	public WebhookHandler getHandler(String id);
}
