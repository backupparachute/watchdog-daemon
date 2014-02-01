package com.kylemiller.watchdogd.util;

public class UriGenerator {

	private String host;
	
	public String generate(String u) {
		
		//generate environment based uri
		return String.format("%s%s", getHost(),u);
		
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
