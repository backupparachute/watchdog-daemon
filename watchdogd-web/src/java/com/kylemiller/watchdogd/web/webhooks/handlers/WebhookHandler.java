package com.kylemiller.watchdogd.web.webhooks.handlers;

public interface WebhookHandler<T> {

	public boolean handle(T obj);
}
