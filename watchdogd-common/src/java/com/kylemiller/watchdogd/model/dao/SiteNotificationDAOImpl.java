package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.kylemiller.watchdogd.model.SiteNotification;

@Repository("siteNotificationDAO")
public class SiteNotificationDAOImpl extends BaseDAOImpl<SiteNotification> implements SiteNotificationDAO {

	@SuppressWarnings("unchecked")
	public SiteNotification findMostRecentNofication(final Integer id) {
		try{
			return (SiteNotification) getEntityManager().createNamedQuery("SiteNotification.findMostRecent")
			.setParameter("id", id)
			.setMaxResults(1)
			.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findSiteStatusCountPerDay failed..."));
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public List<SiteNotification> findUnsent() {
		try{
			return getEntityManager().createNamedQuery("SiteNotification.findUnsent").getResultList();
		} catch (RuntimeException re) {
			logger.error(String.format("SiteNotification.findUnsent failed..."));
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SiteNotification> findAllForSite(final Integer siteId, final Date date, final Integer max) {
		try{
			return getEntityManager().createNamedQuery("SiteNotification.findAllForSite")
					.setParameter("id", siteId)
					.setParameter("date", date)
					.setMaxResults(max)
					.getResultList();
		} catch (RuntimeException re) {
			logger.error(String.format("SiteNotification.findUnsent failed..."));
			throw re;
		}
	}
}
