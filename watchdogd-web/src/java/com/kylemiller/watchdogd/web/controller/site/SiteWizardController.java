package com.kylemiller.watchdogd.web.controller.site;

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

//@Controller
public class SiteWizardController {

	@RequestMapping(value="/site/new")
	public ModelAndView handle() {
		
		Map map = new HashMap();
		//map.put("sites", sites);
		
		return new ModelAndView("site/site-wizard", map);
	}
}
