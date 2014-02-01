package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteQueue;


public interface SiteQueueDAO extends BaseDAO<SiteQueue> {
	public List<SiteQueue> findAll(final Integer max);
	public SiteQueue findById(final Integer id);
	public boolean updateProcessing(final Integer id);
	public boolean deleteBySite(final Site site);
	public boolean deleteBySiteId(final Integer id);
}
