package com.kylemiller.watchdogd.probe.service;

import com.kylemiller.watchdogd.model.SiteReport;

public interface ProbeCallback {

	public void before(SiteReport report);
	public void after(SiteReport report);
	
}
