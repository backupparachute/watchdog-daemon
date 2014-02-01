package com.kylemiller.watchdogd.web.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kylemiller.watchdogd.model.User;

public class CurrentUsersInterceptor extends HandlerInterceptorAdapter {

	
	public static final String ANON_KEY = "ANONYMOUS--";
	private Map currentUsers;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		SecurityContext sc = SecurityContextHolder.getContext();
		if (null == sc) return true;
		
		Authentication auth = sc.getAuthentication();
		if (null == auth) return true;
		
		Object obj = auth.getPrincipal();
		
		User user = null;
		//if (null != obj && obj instanceof User)  s = ((User) obj).getUsername();
		if (null != obj && obj instanceof User)  user = (User) obj;
		//else s = String.format("%s%s", ANON_KEY, request.getRemoteAddr());
		else user = new AnonymousUser(request.getRemoteAddr());
				
		getCurrentUsers().put(user, new Date());
		
		return true;
	}
	
	public class AnonymousUser extends User {
		private String ip;
		
		public AnonymousUser(String ip) {
			this.ip = ip;
		}
		
		@Override
		public String getUsername() {
			return ip;
		}
	}

	public Map getCurrentUsers() {
		return currentUsers;
	}

	public void setCurrentUsers(Map currentUsers) {
		this.currentUsers = currentUsers;
	}
}
