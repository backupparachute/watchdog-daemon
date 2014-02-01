package com.kylemiller.watchdogd.web.controller.site;

import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

@Controller
public class SitesController {

	//@Autowired
	@Resource
	private SiteDAO siteDAO;
	
	@RequestMapping(value="/sites")
	public ResponseEntity<String> handle() {
		
		User user = SecurityHelper.getLoggedInUser();
		List<Site> sites = getSiteDAO().findAllSites(user.getAccount());
		
		JSONArray list = new JSONArray();
		
		for (Site site : sites) {
			JSONObject json = new JSONObject();
			json.put("id", site.getId());
			json.put("name", site.getName());
			json.put("type", site.getType());
			json.put("status", site.getStatus().getStatus());
			json.put("location", String.format("/site/%s/status", site.getId()));
			list.add(json);
		}
		
//		return list.toJSONString();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ResponseEntity<String> entity = new ResponseEntity<String>(list.toJSONString(), headers, HttpStatus.OK);
		
		return entity;
	}
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}
	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}
}
