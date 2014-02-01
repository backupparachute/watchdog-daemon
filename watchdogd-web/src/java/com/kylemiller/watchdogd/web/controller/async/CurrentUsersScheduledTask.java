package com.kylemiller.watchdogd.web.controller.async;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.web.interceptor.CurrentUsersInterceptor.AnonymousUser;

@Component
public class CurrentUsersScheduledTask {

	protected final Logger log = LoggerFactory.getLogger(CurrentUsersScheduledTask.class);
	
	@Resource
	private Map currentUsers;
	@Resource 
	private UserDAO userDAO;
	
	@Scheduled(fixedDelay=300000)
	public void run() {
		Long now = new Date().getTime();
		for (Iterator<Entry<User, Date>> iter = getCurrentUsers().entrySet().iterator(); iter.hasNext();) {
			Entry<User, Date> entry =  iter.next();
			
			Date date = entry.getValue();
			if ((now - date.getTime()) > 300000) {
				User user = entry.getKey();
				//if(!StringUtils.contains(user, CurrentUsersInterceptor.ANON_KEY)){
				if (!user.getClass().isAssignableFrom(AnonymousUser.class)) {
					getUserDAO().updateLastAccessed(user.getUsername());
				}
				
				iter.remove();
				log.debug(String.format("user %s is no longer using watchdogdaemon...", user.getUsername()));
			}
		}
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public Map getCurrentUsers() {
		return currentUsers;
	}

	public void setCurrentUsers(Map currentUsers) {
		this.currentUsers = currentUsers;
	}
	
}
