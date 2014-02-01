package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.model.Contact;

@Repository("contactDAO")
public class ContactDAOImpl extends BaseDAOImpl<Contact> implements ContactDAO {
	
	@SuppressWarnings("unchecked")
	public List<Contact> findAllContacts(final Account account) {
		try{
			return getEntityManager().createNamedQuery("findAllContactsForAccount")
					.setParameter("accountId", account.getId())
					.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findAllContactsForAccount failed..."));
			throw re;
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		delete(id, Contact.class);
	}
	

	@SuppressWarnings("unchecked")
	public Contact findById(final Integer id) {
		try{
			return (Contact) getEntityManager().createNamedQuery("findContactById")
					.setParameter("id", id)
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findContactById failed..."));
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Integer authorize(final Integer id, final Integer accountId) {
		try{
			return (Integer) getEntityManager().createNamedQuery("Contact.authorize")
			.setParameter("contactId", id)
			.setParameter("accountId", accountId)
			.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("authorize failed..."));
			throw re;
		}
	}
}
