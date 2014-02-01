package com.kylemiller.watchdogd.service;

import org.springframework.stereotype.Service;

import com.kylemiller.watchdogd.model.dao.AccountDAO;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	private AccountDAO accountDAO;
	
	public void disableAccount(String token) {
		getAccountDAO().updateEnabled(token, false);
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
}
