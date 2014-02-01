package com.kylemiller.watchdogd.web.controller.admin.request;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.dao.RequestDAO;

@Controller 
public class RequestsController {

	@Resource
	private RequestDAO requestDAO;
	
	@RequestMapping(value="/admin/requests")
	public ModelAndView view() {
		
		
		Map map = new HashMap();
		map.put("requests", getRequestDAO().findAll());
		
		return new ModelAndView("admin/support/requests", map);
	}
	
	public RequestDAO getRequestDAO() {
		return requestDAO;
	}
	public void setRequestDAO(RequestDAO requestDAO) {
		this.requestDAO = requestDAO;
	}
}
