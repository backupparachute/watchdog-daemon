package com.kylemiller.watchdogd.web.interceptor.security;

import javax.annotation.Resource;

import com.kylemiller.watchdogd.model.dao.SiteDAO;

public class SiteUrlSecurityInterceptor extends BaseUrlSecurityInterceptor {

	public SiteUrlSecurityInterceptor() {
		super("/app/site/(\\d{1,}).*?");
	}
	
	@Resource
	private SiteDAO siteDAO;
		
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}

	@Override
	protected Integer findId(Integer siteId, Integer accountId) {
		return getSiteDAO().authorize(siteId, accountId);
	}
	
}
