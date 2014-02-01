package com.kylemiller.watchdogd.probe.service;

import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.dao.SiteQueueDAO;

public class BaseProbeCallback implements ProbeCallback {
	private SiteQueueDAO siteQueueDAO;

	public SiteQueueDAO getSiteQueueDAO() {
		return siteQueueDAO;
	}

	public void setSiteQueueDAO(SiteQueueDAO siteQueueDAO) {
		this.siteQueueDAO = siteQueueDAO;
	}

	@Override
	public void before(SiteReport report) {
		//do nothing
	}

	@Override
	public void after(SiteReport report) {
		getSiteQueueDAO().deleteBySiteId(report.getSite().getId());
	}
}
