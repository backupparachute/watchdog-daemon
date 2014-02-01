package com.kylemiller.watchdogd.web.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/account")
public class AccountDetailsController {

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view() { 
		return new ModelAndView("account/details");
	}
}
