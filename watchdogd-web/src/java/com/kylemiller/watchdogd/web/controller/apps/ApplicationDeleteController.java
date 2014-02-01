package com.kylemiller.watchdogd.web.controller.apps;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.dao.UserDAO;


@Controller
@RequestMapping(value="/application/{id}/delete")
public class ApplicationDeleteController {
	
	private final Logger log = LoggerFactory.getLogger(ApplicationDeleteController.class);
	
	@Resource
	private UserDAO userDAO;
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView delete(@PathVariable("id") Integer id, @RequestParam(value="_delete",required=false) String delete) throws Exception {
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/applications");
		
		if (StringUtils.isNotBlank(delete)) getUserDAO().delete(id);
		
		return mv;
	}
		
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Integer id) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("app/app-delete");
		
		mv.addObject("application", getUserDAO().findById(id));
		
		return mv;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
}
