package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.util.SimplePaginatedList;

public interface SiteReportDAO extends BaseDAO<SiteReport> {
	public List<Map> findSiteStatusCountPerDay(final Integer siteId, final Date date);
	public List<Map> findSiteResponseTimeByHour(final Integer siteId, final Date date);
	public List<SiteReport> findSiteReports(final Integer siteId, final Date date, final Integer start, final Integer max);
	public SimplePaginatedList findSiteReportsPaginated(final Integer page, final Integer siteId, final Date date);
	public List<Map> findSiteReportsByType(final Integer siteId, final Date date);
	public void deleteBySite(final Integer id);
	public void deleteSiteReportsOlderThan(final Date date);
	public List<Map> findSiteValueByHour(final Integer siteId, final Date date);
}
