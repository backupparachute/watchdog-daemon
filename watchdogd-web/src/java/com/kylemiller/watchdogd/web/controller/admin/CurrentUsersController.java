package com.kylemiller.watchdogd.web.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CurrentUsersController {
	
	@Resource
	private Map currentUsers;
	
	@RequestMapping("/admin/users/current")
	public ModelAndView view() {
		
		Map map = new HashMap();
		map.put("currentUsers", getCurrentUsers().keySet());
		
		return new ModelAndView("admin/currentUsers", map);
	}

	public Map getCurrentUsers() {
		return currentUsers;
	}

	public void setCurrentUsers(Map currentUsers) {
		this.currentUsers = currentUsers;
	}

}
