package com.kylemiller.watchdogd.service;

import com.kylemiller.watchdogd.model.SiteReport;

public interface SiteStatusService {

	public abstract void toggleStatus(SiteReport report);

}