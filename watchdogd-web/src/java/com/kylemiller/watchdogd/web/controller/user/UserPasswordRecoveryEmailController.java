package com.kylemiller.watchdogd.web.controller.user;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.util.UriGenerator;

@Controller
@RequestMapping(value="/user/recovery")
public class UserPasswordRecoveryEmailController {

	@Resource
	private UserDAO userDAO;
	@Resource
	private EmailService emailService;
	@Resource
	private UriGenerator uriGenerator;
	@Resource
	private SaltSource saltSource;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view() { 
		return new ModelAndView("users/reset-password-email");
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView send(@RequestParam String email) { 
		
		User user = getUserDAO().findByUsername(email);
		
		String date = DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyMMdd");
		
		String hash = DigestUtils.shaHex(date + getSaltSource().getSalt(user) + user.getId() + user.getPassword());
		
		getEmailService().send(email,"[WatchdogD] Password Reset Link", getUriGenerator().generate(String.format("/user/%s/token/%s-%s",user.getId(), date, hash)));
		
		
		return new ModelAndView("redirect:/home");
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public UriGenerator getUriGenerator() {
		return uriGenerator;
	}

	public void setUriGenerator(UriGenerator uriGenerator) {
		this.uriGenerator = uriGenerator;
	}

	public SaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}
}
