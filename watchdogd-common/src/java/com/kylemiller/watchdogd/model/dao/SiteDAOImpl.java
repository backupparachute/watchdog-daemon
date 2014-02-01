package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.model.Site;

@Repository("siteDAO")
public class SiteDAOImpl extends BaseDAOImpl<Site> implements SiteDAO {

	@SuppressWarnings("unchecked")
	public List<Site> findSitesToMonitor(final Date interval) {
		try {
				return getEntityManager().createNamedQuery("Site.findSitesToMonitor")
				.setParameter("interval", interval)
				.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findSitesToMonitor failed..."));
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Site> findAllSites(final Account account) {
		try {
			return getEntityManager().createNamedQuery("Site.findAllSitesForAccount")
			.setParameter("accountId", account.getId())
			.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findAllSitesForAccount failed..."));
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	//public List<Site> findDownSites(final Date date) {
	public List<Site> findDownSites(final Date date) {
		try {
			return getEntityManager().createNamedQuery("Site.findDownSites")
			.setParameter("date", date)
			//.setParameter("subId", subId)
			.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findDownSites failed..."));
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Site findById(final Integer id) {
		try {
			return (Site) getEntityManager().createNamedQuery("Site.findSiteById")
			.setParameter("id", id)
			.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findSiteById failed..."));
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Integer authorize(final Integer siteId, final Integer accountId) {
		try {
			return (Integer) getEntityManager().createNamedQuery("Site.authorize")
			.setParameter("siteId", siteId)
			.setParameter("accountId", accountId)
			.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (NoResultException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("authorize failed..."));
			throw re;
		}
	}

	@Transactional
	public void delete(Integer id) {
		delete(id, Site.class);
	}
}
