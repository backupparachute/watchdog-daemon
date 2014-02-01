package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.kylemiller.watchdogd.model.Subscription;

//@Repository("subscriptionDAO")
@Deprecated
public class SubscriptionDAOImpl extends BaseDAOImpl<Subscription> implements SubscriptionDAO {
	
	@SuppressWarnings("unchecked")
	public List<Subscription> findSubscriptions() {
		try{
					Query query = getEntityManager().createNamedQuery("findSubscriptions");
					return query.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findSubscriptions failed..."));
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public Subscription findByName(final String name) {
		try{
			Query query = getEntityManager().createNamedQuery("Subscription.findByName");
			query.setParameter("name", name);
			return (Subscription) query.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("Subscriptions.findByName failed..."));
			throw re;
		}
	}

}
