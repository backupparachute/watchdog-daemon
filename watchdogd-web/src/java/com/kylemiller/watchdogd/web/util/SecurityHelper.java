package com.kylemiller.watchdogd.web.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kylemiller.watchdogd.model.Group;
import com.kylemiller.watchdogd.model.User;

public class SecurityHelper {

	public static User getLoggedInUser() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (null == obj || obj instanceof String) return null;
		
		return (User) obj;
	}
	
	public static boolean isUserAuthenticated() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (null != obj && obj instanceof User) return true;
		
		return false;
	}
	
	public static boolean isUserInGroup(User user, String group) {
		
		for(Group g : user.getGroups()) {
			if (StringUtils.equals(group, g.getName())) return true;
		}
		
		return false;
	}
}
