package com.kylemiller.watchdogd.web.controller.help;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Help;
import com.kylemiller.watchdogd.model.dao.HelpDAO;

@Controller
@RequestMapping(value="/admin/help/{name}")
public class HelpUpdateController {

	@Resource
	private HelpDAO helpDAO;
	
	@RequestMapping( method=RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("command") Help command) throws Exception {
		
		getHelpDAO().update(command);
		
		return new ModelAndView("redirect:/help/"+command.getUrl());
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@ModelAttribute("command") Help command) throws Exception {
		
		ModelAndView model = new ModelAndView("support/help-edit");
		
		return model;
	}
	
	
	@ModelAttribute("command")
	public Help createModel(@PathVariable("name") String name) {
		
		Help help = getHelpDAO().findByUrl(name);
		
		return (null == help) ? new Help(name) : help;
	}

	public HelpDAO getHelpDAO() {
		return helpDAO;
	}

	public void setHelpDAO(HelpDAO helpDAO) {
		this.helpDAO = helpDAO;
	}

}
