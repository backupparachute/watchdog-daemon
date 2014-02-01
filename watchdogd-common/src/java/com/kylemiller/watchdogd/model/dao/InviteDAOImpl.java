package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.kylemiller.watchdogd.model.Invite;

@Repository("inviteDAO")
public class InviteDAOImpl extends BaseDAOImpl<Invite> implements InviteDAO {
	
	@SuppressWarnings("unchecked")
	public List<Invite> findAll() {
		try{
			return getEntityManager().createNamedQuery("Invite.findAll").getResultList();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findAll invites failed...", re));
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Invite findByUuid(final String uuid) {
		try{
					return (Invite) getEntityManager().createNamedQuery("Invite.findByUuid")
					.setParameter("uuid", uuid)
					.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findByUuid invites failed...", re));
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Long numberOfInvites(final Integer id) {
		try{
			return (Long) getEntityManager().createNamedQuery("Invite.numInvites")
				.setParameter("id", id)
				.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return 0l;
		} catch (RuntimeException re) {
			logger.error(String.format("numberOfInvites failed...", re));
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public Invite findByEmail(final String email) {
		try{
			return (Invite) getEntityManager().createNamedQuery("Invite.findByEmail")
			.setParameter("email", email)
			.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (RuntimeException re) {
			logger.error(String.format("findByEmail invites failed...", re));
			throw re;
		}
	}
	
	
}
