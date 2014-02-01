package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Help;

@Repository("helpDAO")
public class HelpDAOImpl extends BaseDAOImpl<Help> implements HelpDAO {
	
	@SuppressWarnings("unchecked")
	public List<Help> findAllHelp() {
		try{
			return getEntityManager().createNamedQuery("Help.findAll")
					.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("Help.findAll failed..."));
			throw re;
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		delete(id, Help.class);
	}
	
	@SuppressWarnings("unchecked")
	public Help findByUrl(final String s) {
		try{
			return (Help) getEntityManager().createNamedQuery("Help.findByUrl")
					.setParameter("url", s)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("Help.findByUrl failed..."));
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Help findById(final Integer id) {
		try{
			return (Help) getEntityManager().createNamedQuery("Help.findById")
					.setParameter("id", id)
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("Help.findById failed..."));
			throw re;
		}
	}
}
