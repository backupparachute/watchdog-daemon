package com.kylemiller.watchdogd.web.controller.user;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.util.DateHelper;

@Controller
@RequestMapping(value="/user/{id}/token/{token}")
public class UserPasswordResetController {

	@Resource
	private DateHelper dateHelper;
	@Resource
	private UserDAO userDAO;
	@Resource
	private SaltSource saltSource;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(@PathVariable String token, @ModelAttribute User user) { 
		
		if (verifyToken(token, user)) {
			return new ModelAndView("users/reset-password");
		} else {
			return new ModelAndView("redirect:/");
		}
		
	}

	protected boolean verifyToken(String token, User user) {
		String date = StringUtils.substringBefore(token, "-");
		String hash = StringUtils.substringAfter(token, "-");
		
		String s = DigestUtils.shaHex(date + getSaltSource().getSalt(user) + user.getId() + user.getPassword());
		
		Date now = new Date();
		
		Date d = getDateHelper().parse(date);
		
		return null != d && now.compareTo(d) <= 0 && StringUtils.equalsIgnoreCase(hash, s);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView send(@PathVariable String token, @ModelAttribute User user, @RequestParam(value="_pass") String pass) {
		
		if (!verifyToken(token, user)) return new ModelAndView("redirect:/");
		
		user.setPassword(pass);
		
		getUserDAO().updateAndEncode(user);
		
		return new ModelAndView("redirect:/home");
	}
	
	@ModelAttribute
	public User createModel(@PathVariable("id") Integer id) {
		
		return getUserDAO().findById(id);
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public DateHelper getDateHelper() {
		return dateHelper;
	}

	public void setDateHelper(DateHelper dateHelper) {
		this.dateHelper = dateHelper;
	}

	public SaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}
}
