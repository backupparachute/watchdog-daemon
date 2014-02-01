package com.kylemiller.watchdogd.web.util;

import javax.servlet.http.HttpSession;

public class AttributeFinder {
	
	private HttpSession session;
	
	public AttributeFinder(HttpSession session) {
		this.session = session;
	}
	
	public Object find(String name) {
		return session.getAttribute(name);
	}

}
