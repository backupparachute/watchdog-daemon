package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.util.SimplePaginatedList;

@Repository("siteReportDAO")
public class SiteReportDAOImpl extends BaseDAOImpl<SiteReport> implements SiteReportDAO {

	@SuppressWarnings("unchecked")
	public List<Map> findSiteStatusCountPerDay(final Integer siteId, final Date date) {
		try{
			return getEntityManager().createNamedQuery("SiteReport.findSiteStatusCountPerDay")
			.setParameter("siteId", siteId)
			.setParameter("date", date)
			.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findSiteStatusCountPerDay failed..."));
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> findSiteReportsByType(final Integer siteId, final Date date) {
		try{
			return getEntityManager().createNamedQuery("findSiteReportsByType")
			.setParameter("siteId", siteId)
			.setParameter("date", date)
			.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findSiteReportsByType failed..."));
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> findSiteResponseTimeByHour(final Integer siteId, final Date date) {
		try{
			return getEntityManager().createNamedQuery("findSiteResponseTimeByHour")
			.setParameter("id", siteId)
			.setParameter("date", date)
			.getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (RuntimeException re) {
		logger.error(String.format("findSiteResponseTime failed..."));
		throw re;
	}
}
	
@SuppressWarnings("unchecked")
public List<Map> findSiteValueByHour(final Integer siteId, final Date date) {
	try{
		return getEntityManager().createNamedQuery("SiteReport.findSiteValueByHour")
		.setParameter("id", siteId)
		.setParameter("date", date)
		.getResultList();
} catch (EmptyResultDataAccessException e) {
	return null;
} catch (RuntimeException re) {
	logger.error(String.format("findSiteResponseTime failed..."));
		throw re;
	}
}
@SuppressWarnings("unchecked")
public List<SiteReport> findSiteReports(final Integer siteId, final Date date, final Integer start, final Integer max) {
	try{
			Query query = getEntityManager().createNamedQuery("findSiteReportsForTimePeriod");
			query.setParameter("id", siteId);
			query.setParameter("date", date);
			if (null != start) query.setFirstResult(start);
			if (null != max) query.setMaxResults(max);
			return query.getResultList();
} catch (EmptyResultDataAccessException e) {
	return null;
	} catch (RuntimeException re) {
		logger.error(String.format("findSiteReports failed..."));
		throw re;
	}
}
@SuppressWarnings("unchecked")
public Long findSiteReportsCount(final Integer siteId, final Date date) {
	try{
				Query query = getEntityManager().createNamedQuery("findSiteReportsForTimePeriodCount");
				query.setParameter("id", siteId);
				query.setParameter("date", date);
				return (Long) query.getSingleResult();
	} catch (EmptyResultDataAccessException e) {
		return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findSiteReportsForTimePeriodCount failed..."));
			throw re;
		}
	}
	
	public SimplePaginatedList findSiteReportsPaginated(final Integer page, final Integer siteId, final Date date) {
		
		SimplePaginatedList list = new SimplePaginatedList();
		list.setObjectsPerPage(300);
		list.setPageNumber(page);
		
		Long count = findSiteReportsCount(siteId, date);
		list.setFullListSize(count.intValue());
		
		List reports = findSiteReports(siteId, date, list.calculateStartingIndex(), list.getObjectsPerPage());
		list.setList(reports);
		
		return list;
	}
	
	@Transactional
	public void deleteBySite(final Integer id) {
		try{
			final String queryString = String.format("delete from SiteReport model where model.site.id = :id");
					Query query = getEntityManager().createQuery(queryString);
					query.setParameter("id", id);
					query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error(String.format("delete SiteReport by site.id: %s failed", id), re);
			throw re;
		}
		logger.info("delete successful");
	}
	@Transactional
	public void deleteSiteReportsOlderThan(final Date date) {
		try{
			Query query = getEntityManager().createNamedQuery("deleteSiteReportsOlderThan");
			query.setParameter("date", date);
			query.executeUpdate();
	} catch (RuntimeException re) {
		logger.error(String.format("delete SiteReport older than days", date), re);
		throw re;
	}
	logger.info("delete successful");
	}
	
}
