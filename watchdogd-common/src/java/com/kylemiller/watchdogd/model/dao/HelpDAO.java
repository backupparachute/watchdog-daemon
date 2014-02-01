package com.kylemiller.watchdogd.model.dao;

import java.util.List;

import com.kylemiller.watchdogd.model.Help;


public interface HelpDAO extends BaseDAO<Help> {
	public List<Help> findAllHelp();
	public Help findById(final Integer id);
	public Help findByUrl(final String s);
}
