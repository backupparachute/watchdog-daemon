package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteQueue;

@Repository("siteQueueDAO")
public class SiteQueueDAOImpl extends BaseDAOImpl<SiteQueue> implements SiteQueueDAO {
	
	@SuppressWarnings("unchecked")
	public List<SiteQueue> findAll(final Integer max) {
		try{
			return getEntityManager().createNamedQuery("SiteQueue.findAll")
			.setMaxResults(max)
			.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("sitequeue findAll failed...", re));
			throw re;
		}
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean updateProcessing(final Integer id) {
		try {
			Integer i = getEntityManager().createNamedQuery("SiteQueue.updateProcessing")
					.setParameter("id", id)
					.setParameter("date", new Date())
					.executeUpdate();
			return (i == 1);
		} catch (EmptyResultDataAccessException e) {
			return false;
		} catch (RuntimeException re) {
			logger.error(String.format("update processed failed, id: ", id), re);
			throw re;
		}
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean deleteBySite(final Site site) {
		return deleteBySiteId(site.getId());
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean deleteBySiteId(final Integer id) {
		try {
			Integer i = (Integer) getEntityManager().createNamedQuery("SiteQueue.deleteBySite").setParameter("id", id).executeUpdate();
			return (i == 1);
		} catch (EmptyResultDataAccessException e) {
			return false;
		} catch (RuntimeException re) {
			logger.error(String.format("delete site queue by site failed, id: ", id), re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public SiteQueue findById(final Integer id) {
		try{
			return (SiteQueue) getEntityManager().createNamedQuery("SiteQueue.findById").setParameter("id", id).getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("site queue find by id failed...", re));
			throw re;
		}
	}
	
	
}
