package com.kylemiller.watchdogd.model.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.kylemiller.watchdogd.model.Service;

@Repository("serviceDAO")
public class ServiceDAOImpl extends BaseDAOImpl<Service> implements ServiceDAO {
	
	@SuppressWarnings("unchecked")
	public Service findByName(final String name) {
		try{
			return (Service) getEntityManager().createNamedQuery("Service.findByName")
			.setParameter("name", name)
			.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("Service.findByName failed...", re));
			throw re;
		}
	}
}
