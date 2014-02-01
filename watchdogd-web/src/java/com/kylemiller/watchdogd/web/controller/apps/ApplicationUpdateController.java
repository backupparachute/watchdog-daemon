package com.kylemiller.watchdogd.web.controller.apps;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.UserDAO;


@Controller
public class ApplicationUpdateController {
	
	@Resource
	private UserDAO userDAO;
	
	@RequestMapping(value="/application/{id}/edit",method=RequestMethod.POST)
	protected ModelAndView update(@ModelAttribute User command) throws Exception {
		
		getUserDAO().update(command);
		
		return new ModelAndView("redirect:/applications");
	}	
	
	@RequestMapping(value="/application/{id}/edit",method=RequestMethod.GET)
	public ModelAndView view(@ModelAttribute User command) throws Exception {
		
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("app/app-edit");
		mv.addObject("command", command);
		
		return mv;
	}
	
	@ModelAttribute
	public User createModel(@PathVariable("id") Integer id) {
		
		return getUserDAO().findById(id);
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
