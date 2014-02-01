package com.kylemiller.watchdogd.model.dao;

import com.kylemiller.watchdogd.model.Account;

public interface AccountDAO extends BaseDAO<Account> {
	public boolean updateEnabled(final String token, final boolean enabled);
}
