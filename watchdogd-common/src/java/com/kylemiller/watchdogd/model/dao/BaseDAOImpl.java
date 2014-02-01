package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Identifiable;


public abstract class BaseDAOImpl<T extends Identifiable> implements BaseDAO<T> {
	
	protected final Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Transactional
	public void save(T entity) {
		logger.info(String.format("saving %s instance", entity.getClass().getSimpleName()));
		try {
			getEntityManager().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}
	
	public void refresh(T entity) {
		logger.info(String.format("refreshing %s instance", entity.getClass().getSimpleName()));
		try {
			getEntityManager().refresh(entity);
			logger.info("refresh successful");
		} catch (RuntimeException re) {
			logger.error("refresh failed", re);
			throw re;
		}
	}
	
	@Transactional
	public void updateAll(List<T> entities) {
		//logger.info(String.format("updating all %s...", entities.));
		try {
			for (T entity : entities) {
				getEntityManager().merge(entity);
			}
		} catch (RuntimeException re) {
			logger.error("update all failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(T entity) {
		logger.info(String.format("deleting %s instance", entity.getClass()));
		try {
			entity = (T) getEntityManager().getReference(entity.getClass(), entity.getId());
			//refresh(entity);
//			T result = getJpaTemplate().merge(entity);
			getEntityManager().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}
	
	
	@Transactional
	protected void delete(final Integer id, Class cls) {
		try{
			final String queryString = String.format("delete from %s model where model.id= :id", cls.getSimpleName(), id);
			getEntityManager().createQuery(queryString)
							.setParameter("id", id)
							.executeUpdate();
		} catch (RuntimeException re) {
			logger.error(String.format("delete %s by id: %s failed",cls.getSimpleName(), id), re);
			throw re;
		}
		logger.info("delete successful");
	}
	
	@Transactional
	public T update(T entity) {
		logger.info(String.format("updating %s instance", entity.getClass().getSimpleName()));
		try {
			T result = getEntityManager().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}