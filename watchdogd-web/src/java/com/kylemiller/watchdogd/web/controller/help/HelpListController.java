package com.kylemiller.watchdogd.web.controller.help;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.dao.HelpDAO;


@Controller
public class HelpListController {

	@Resource
	private HelpDAO helpDAO;
	
	@RequestMapping(value="/help")
	public ModelAndView view() throws Exception {
		
		Map map = new HashMap();
		map.put("helpList", getHelpDAO().findAllHelp() );
		
		return new ModelAndView("support/help-list",map);
	}

	public HelpDAO getHelpDAO() {
		return helpDAO;
	}

	public void setHelpDAO(HelpDAO helpDAO) {
		this.helpDAO = helpDAO;
	}
}
