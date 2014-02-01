package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import com.kylemiller.watchdogd.model.Invite;


public interface InviteDAO extends BaseDAO<Invite> {
	public List<Invite> findAll();
	public Invite findByUuid(final String uuid);
	public Invite findByEmail(final String email);
	public Long numberOfInvites(final Integer id);
}
