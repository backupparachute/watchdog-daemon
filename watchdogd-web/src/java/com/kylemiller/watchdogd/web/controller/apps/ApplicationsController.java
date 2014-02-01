package com.kylemiller.watchdogd.web.controller.apps;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

@Controller
@RequestMapping(value="/applications")
public class ApplicationsController {
	
	private final Logger log = LoggerFactory.getLogger(ApplicationsController.class);
	@Resource
	private UserDAO userDAO;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView("app/apps");
		model.addObject("applications", getUserDAO().findByGroup("application", SecurityHelper.getLoggedInUser().getAccount().getId()));
		
		return model;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
