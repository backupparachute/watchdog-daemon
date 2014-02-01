package com.kylemiller.watchdogd.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

@Controller 
public class HomeController {

	@Resource
	private SiteDAO siteDAO;
	
	@RequestMapping(value="/home")
	public ModelAndView handle() {
		
		User user = SecurityHelper.getLoggedInUser();
		
		if (null == user) return new ModelAndView("redirect:/");
		
		List<Site> sites = getSiteDAO().findAllSites(user.getAccount());
		
		Map map = new HashMap();
		
		map.put("sites", sites);
		
		return new ModelAndView("home", map);
	}
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}
	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}
	
}
