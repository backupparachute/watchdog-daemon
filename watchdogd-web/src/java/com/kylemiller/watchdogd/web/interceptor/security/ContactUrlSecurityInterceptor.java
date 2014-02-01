package com.kylemiller.watchdogd.web.interceptor.security;

import javax.annotation.Resource;

import com.kylemiller.watchdogd.model.dao.ContactDAO;

public class ContactUrlSecurityInterceptor extends BaseUrlSecurityInterceptor {

	public ContactUrlSecurityInterceptor() {
		super("/app/contact/(\\d{1,}).*?");
	}
	
	@Resource
	private ContactDAO contactDAO;
	
	@Override
	protected Integer findId(Integer siteId, Integer accountId) {
		return getContactDAO().authorize(siteId, accountId);
	}

	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}
	
}
