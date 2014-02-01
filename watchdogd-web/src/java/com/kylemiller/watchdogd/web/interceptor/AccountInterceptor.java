package com.kylemiller.watchdogd.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kylemiller.watchdogd.model.User;

public class AccountInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		
		if (StringUtils.contains(uri, "/account/payment")) return true;
		
		SecurityContext sc = SecurityContextHolder.getContext();
		if (null == sc) return true;
		
		Authentication auth = sc.getAuthentication();
		if (null == auth) return true;
		
		Object obj = auth.getPrincipal();
		
		User user = null;
		//if (null != obj && obj instanceof User)  s = ((User) obj).getUsername();
		if (null != obj && obj instanceof User)  user = (User) obj;
		//else s = String.format("%s%s", ANON_KEY, request.getRemoteAddr());
		else return true;
				
		if (!user.getAccount().isEnabled()) {
			response.sendRedirect("/account/payment");
			return false;
		}
		
		return true;
	}
	
}
