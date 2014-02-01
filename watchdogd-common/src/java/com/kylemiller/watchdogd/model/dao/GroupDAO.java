package com.kylemiller.watchdogd.model.dao;

import com.kylemiller.watchdogd.model.Group;


public interface GroupDAO extends BaseDAO<Group> {
	public Group findByName(final String name);
}
