package com.kylemiller.watchdogd.web.controller.site;

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

import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.web.service.sites.SiteService;


@Controller
@RequestMapping(value="/site/{id}/delete")
public class SiteDeleteController {
	
	private final Logger log = LoggerFactory.getLogger(SiteDeleteController.class);
	
	@Resource
	private SiteDAO siteDAO;
	@Resource
	private SiteService siteService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView delete(@PathVariable("id") Integer id, @RequestParam(value="_delete",required=false) String delete) throws Exception {
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/home");
		
		if (StringUtils.isNotBlank(delete)) getSiteService().delete(id);
		
		return mv;
	}
		
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Integer id) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("site/site-delete");
		
		mv.addObject("site", getSiteDAO().findById(id));
		
		return mv;
	}
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
}
