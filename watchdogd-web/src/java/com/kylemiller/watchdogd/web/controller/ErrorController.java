package com.kylemiller.watchdogd.web.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.web.util.SecurityHelper;

@Controller 
public class ErrorController {

	private final Logger log = LoggerFactory.getLogger(ErrorController.class);
	
	@RequestMapping(value="/error/{err}")
	public ModelAndView handle(@PathVariable("err") String err, HttpSession sess) {
		
		Map map = new HashMap();
		map.put("err", err);
		
		Object obj = sess.getAttribute("errex");
		if (null != obj) {
			Exception e = (Exception) obj;
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.error(String.format("Error caught in the interface for user: %s, ex: %s", SecurityHelper.getLoggedInUser(), sw.toString()));
			sess.removeAttribute("errex");
		}
		
		
		return new ModelAndView("error",map);
	}
	
}
