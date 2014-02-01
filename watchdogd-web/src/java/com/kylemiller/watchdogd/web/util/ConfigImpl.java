package com.kylemiller.watchdogd.web.util;

import org.springframework.stereotype.Component;

@Component
public class ConfigImpl implements Config {
	
	private String apiKey = "KEYLGsdVpjFzjbcTvid3VR4giTsknXgh";
	private String subscription = "no-trial";
//	private String subscription = "standard";
	private String group = "user";
	private String inviteCount = "5";
	private Integer downSiteInterval = -5;
	
	@Override
	public String getApiKey() {
		return apiKey;
	}
	
	@Override
	public String getSubscription() {
		return subscription;
	}
	public String getGroup() {
		return group;
	}
	public String getInviteCount() {
		return inviteCount;
	}

	public Integer getDownSiteInterval() {
		return downSiteInterval;
	}

}
