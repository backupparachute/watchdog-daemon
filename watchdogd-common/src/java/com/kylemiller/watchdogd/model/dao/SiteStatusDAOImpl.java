package com.kylemiller.watchdogd.model.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.SiteStatus;

@Repository("siteStatusDAO")
public class SiteStatusDAOImpl extends BaseDAOImpl<SiteStatus> implements SiteStatusDAO {
	
	@Transactional
	public void deleteBySite(final Integer id) {
		try{
			final String queryString = String.format("delete from SiteStatus model where model.site.id = :id");
					Query query = getEntityManager().createQuery(queryString);
					query.setParameter("id", id);
					query.executeUpdate();
		} catch (RuntimeException re) {
			logger.error(String.format("delete SiteStatus by site.id: %s failed", id), re);
			throw re;
		}
		logger.info("delete successful");
	}
}
