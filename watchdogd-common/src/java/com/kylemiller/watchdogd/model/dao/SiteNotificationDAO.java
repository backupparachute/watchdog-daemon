package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;

import com.kylemiller.watchdogd.model.SiteNotification;

public interface SiteNotificationDAO extends BaseDAO<SiteNotification> {
	public abstract SiteNotification findMostRecentNofication(final Integer id);
	public List<SiteNotification> findUnsent();
	public List<SiteNotification> findAllForSite(final Integer siteId, final Date date, final Integer max);
}
