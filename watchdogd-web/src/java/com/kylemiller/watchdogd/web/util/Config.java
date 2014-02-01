package com.kylemiller.watchdogd.web.util;

public interface Config {

	public abstract String getApiKey();

	public abstract String getSubscription();
	public abstract String getGroup();
	public abstract String getInviteCount();
	public abstract Integer getDownSiteInterval();

}