package com.kylemiller.watchdogd.model.dao;

import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.Account;

@Repository("accountDAO")
public class AccountDAOImpl extends BaseDAOImpl<Account> implements AccountDAO {
	
	@Transactional
	public boolean updateEnabled(final String token, final boolean enabled){
		try{
					Query query = getEntityManager().createNamedQuery("Account.updateEnabled");
					query.setParameter("token", token);
					query.setParameter("enabled", enabled);
					Integer i =  query.executeUpdate();
			return (i == 1);
		} catch (EmptyResultDataAccessException e) {
			return false;
		} catch (RuntimeException re) {
			logger.error(String.format("update enabled for account: %s failed", token), re);
			throw re;
		}
	}
	
}
