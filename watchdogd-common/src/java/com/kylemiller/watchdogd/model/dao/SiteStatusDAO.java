package com.kylemiller.watchdogd.model.dao;

import com.kylemiller.watchdogd.model.SiteStatus;

public interface SiteStatusDAO extends BaseDAO<SiteStatus> {
	
	public void deleteBySite(final Integer id);
}
