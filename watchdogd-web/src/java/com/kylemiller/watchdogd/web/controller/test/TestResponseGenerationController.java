package com.kylemiller.watchdogd.web.controller.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestResponseGenerationController {
	
	@RequestMapping(value="/test/response-gen/{code}")
	public void generate(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer code) throws IOException {
		response.setStatus(code);
		response.setContentType("text/plain");
		response.getWriter().write("watchdog daemon response generator: "+code);
		response.getWriter().flush();
	}

}
