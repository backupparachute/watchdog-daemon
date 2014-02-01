package com.kylemiller.watchdogd.web.controller.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestWaitGenerationController {
	
	@RequestMapping(value="/test/wait/{time}")
	public void generate(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer time) throws IOException {
		
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//ignore
		}
		response.setStatus(200);
		response.setContentType("text/plain");
		response.getWriter().write("watchdog daemon timeout response generator");
		response.getWriter().flush();
	}

}
