package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.model.Site;

public interface SiteDAO extends BaseDAO<Site> {
	public List<Site> findSitesToMonitor(final Date interval);
	public List<Site> findAllSites(final Account account);
	public Site findById(final Integer id);
	public void delete(Integer id);
	//public List<Site> findDownSites(final Date date);
	//public List<Site> findDownSites();
	public List<Site> findDownSites(final Date date);
	public Integer authorize(final Integer siteId, final Integer accountId);
}
