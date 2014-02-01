package com.kylemiller.watchdogd.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.web.bind.annotation.PathVariable;

import com.kylemiller.watchdogd.model.Service;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.ServiceDAO;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

//@Controller 
public class JsonServiceController {

	//@Resource
	private ServiceDAO serviceDAO;
	
	//@RequestMapping(value="/rest/{name}/**")
	//@ResponseBody
	public String handle(HttpServletRequest request, @PathVariable String name) {
		
		Service service = getServiceDAO().findByName(name);
		
		JSONObject params = (JSONObject) JSONValue.parse(service.getParams());
		
		User user = SecurityHelper.getLoggedInUser();
		
		return name+" ### "+ request.getRequestURI();
	}

	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public void setServiceDAO(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}
	
}
