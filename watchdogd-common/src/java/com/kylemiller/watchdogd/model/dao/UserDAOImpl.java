package com.kylemiller.watchdogd.model.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kylemiller.watchdogd.model.User;

@Repository("userDAO")
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

	@Resource
	private PasswordEncoder passwordEncoder;
	@Resource
	private SaltSource saltSource;
	
	public User findById(final Integer userId){
		try{
					Query query = getEntityManager().createNamedQuery("User.findById");
					query.setParameter("id", userId);
					return (User) query.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("find by user id: %s failed", userId), re);
			throw re;
		}
	}
	
	public User findByToken(final String token){
		try{
			Query query = getEntityManager().createNamedQuery("User.findByToken");
			query.setParameter("token", StringUtils.lowerCase(token));
			return (User) query.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (NoResultException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("find by token: %s failed", token), re);
			throw re;
		}
	}
	public User findByUsername(final String username){
		try{
					Query query = getEntityManager().createNamedQuery("User.findByUsername");
					query.setParameter("username", StringUtils.lowerCase(username));
					return (User) query.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (NoResultException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("find by username: %s failed", username), re);
			throw re;
		}
	}
	public List<User> findByGroup(final String group, final Integer accountId){
		try{
					Query query = getEntityManager().createNamedQuery("User.findByGroup");
					query.setParameter("name", group);
					query.setParameter("id", accountId);
					return query.getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (NoResultException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("find by group: %s failed", group), re);
			throw re;
		}
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			return findByUsername(username);
		} catch (EmptyResultDataAccessException e) {
			throw new UsernameNotFoundException(username+" not found.");
		}
	}
	
	@Transactional
	public void saveAndEncode(User user) {
		encodePassword(user);
		save(user);
	}

	private void encodePassword(User user) {
		String pass = user.getPassword();
		String enc = getPasswordEncoder().encodePassword(pass, getSaltSource().getSalt(user));
		user.setPassword(enc);
	}
	
	@Transactional
	public void updateAndEncode(User user) {
		encodePassword(user);
		update(user);
	}
	
	public boolean verifyPassword(User user, String password) {
		return getPasswordEncoder().isPasswordValid(user.getPassword(), password, getSaltSource().getSalt(user));
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void delete(Integer id) {
		delete(id, User.class);
	}
	
	@Transactional
	public boolean updateLastAccessed(final String username){
		try{
					Query query = getEntityManager().createNamedQuery("User.lastAccessed");
					query.setParameter("username", username);
					query.setParameter("date", new Date(), TemporalType.TIMESTAMP);
					Integer i =  query.executeUpdate();
			return (i == 1);
		} catch (EmptyResultDataAccessException e) {
			return false;
		} catch (RuntimeException re) {
			logger.error(String.format("update last accessed for user: %s failed", username), re);
			throw re;
		}
	}
	public SaltSource getSaltSource() {
		return saltSource;
	}
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}
	
}
