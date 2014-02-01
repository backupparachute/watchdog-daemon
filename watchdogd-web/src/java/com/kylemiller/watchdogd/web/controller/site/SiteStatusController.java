package com.kylemiller.watchdogd.web.controller.site;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.web.service.sites.SiteService;


@Controller
public class SiteStatusController {
	
	@Resource
	private SiteService siteService;
	@Resource
	private SiteDAO siteDAO;
	
	@RequestMapping(value="/site/{id}/status",method=RequestMethod.GET)
	public ResponseEntity<String> view(@ModelAttribute Site site, @PathVariable("id") Integer id) throws Exception {
		
		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("status", site.getStatus().getStatus());
		json.put("name", site.getName());
		json.put("type", site.getType());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ResponseEntity<String> entity = new ResponseEntity<String>(json.toJSONString(), headers, HttpStatus.OK);
		
		//return json.toJSONString();
		return entity;
	}
	
	@ModelAttribute
	public Site createModel(@PathVariable("id") Integer id) {
		
		 Site site = getSiteDAO().findById(id);
		return site;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}
	
}
