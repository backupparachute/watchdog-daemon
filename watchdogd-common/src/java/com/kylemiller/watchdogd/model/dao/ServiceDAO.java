package com.kylemiller.watchdogd.model.dao;

import com.kylemiller.watchdogd.model.Invite;
import com.kylemiller.watchdogd.model.Service;


public interface ServiceDAO extends BaseDAO<Service> {
	public Service findByName(final String name);
}
