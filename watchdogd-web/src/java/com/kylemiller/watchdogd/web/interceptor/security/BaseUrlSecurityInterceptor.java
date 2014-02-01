package com.kylemiller.watchdogd.web.interceptor.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

public abstract class BaseUrlSecurityInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = LoggerFactory.getLogger(BaseUrlSecurityInterceptor.class);
	private Pattern regex;
	
	public BaseUrlSecurityInterceptor(String regex) {
		this.regex = Pattern.compile(regex);
	}
	
	@Resource
	private SiteDAO siteDAO;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		log.debug(String.format("request: %s received...", uri));
		
		Matcher m = regex.matcher(uri);
		boolean b = m.find();
		
		if (!b) return true;
		
		log.info(String.format("request: %s requires security check...", uri));
	
		Account a = SecurityHelper.getLoggedInUser().getAccount();
		
		Integer i = findId(new Integer(m.group(1)), a.getId());
		if (null != i && i > 0) {
			log.debug(String.format("account: %s request: %s allowed", a.getId(), uri));
			return true;
		}
			
		log.info(String.format("account: %s request: %s denied", a.getId(), uri));
		response.sendRedirect("/error/403");
		return false;
	}
	
	protected abstract Integer findId(Integer siteId, Integer accountId);

	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}
	
}
