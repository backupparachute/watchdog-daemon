package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.kylemiller.watchdogd.model.Request;

@Repository("requestDAO")
public class RequestDAOImpl extends BaseDAOImpl<Request> implements RequestDAO {
	
	@SuppressWarnings("unchecked")
	public List<Request> findAll() {
		try{
			return getEntityManager().createNamedQuery("Request.findAll").getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("Request.findAll failed..."));
			throw re;
		}
	}
	
	/*
	@SuppressWarnings("unchecked")
	public Contact findById(final Integer id) {
		try{
			return (Contact) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createNamedQuery("findContactById");
					query.setParameter("id", id);
					return query.getSingleResult();
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findContactById failed..."));
			throw re;
		}
	}
	*/
}
