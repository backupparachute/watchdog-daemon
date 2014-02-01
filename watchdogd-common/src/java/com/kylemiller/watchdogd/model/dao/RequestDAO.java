package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import com.kylemiller.watchdogd.model.Request;


public interface RequestDAO extends BaseDAO<Request> {
	public List<Request> findAll();
}
