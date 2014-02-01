package com.kylemiller.watchdogd.web.service.invite;

import java.util.Map;

public interface InviteService {

	public abstract boolean addInvitesRemaining(Map map, Integer userId);
	public boolean addInvitesRemaining(Map map);

}