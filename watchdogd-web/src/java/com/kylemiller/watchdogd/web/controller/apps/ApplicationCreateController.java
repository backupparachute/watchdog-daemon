package com.kylemiller.watchdogd.web.controller.apps;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Group;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.AccountDAO;
import com.kylemiller.watchdogd.model.dao.GroupDAO;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.util.RandomService;
import com.kylemiller.watchdogd.web.util.SecurityHelper;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;
import com.kylemiller.watchdogd.web.validators.ApplicationValidator;

@Controller
@RequestMapping(value="/application/new")
public class ApplicationCreateController {
	
	private final Logger log = LoggerFactory.getLogger(ApplicationCreateController.class);
	@Resource
	private AccountDAO accountDAO;
	@Resource
	private GroupDAO groupDAO;
	@Resource
	private EmailService emailService;
	@Resource
	private RandomService randomService;
	@Resource
	private UserDAO userDAO;
	@Resource
	UiMessageHelper uiMessageHelper;

	public RandomService getRandomService() {
		return randomService;
	}

	public void setRandomService(RandomService randomService) {
		this.randomService = randomService;
	}
	
	   @InitBinder
	    public void initBinder(WebDataBinder binder) {
	    	binder.setValidator(new ApplicationValidator());
	    }

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(@Valid User command, BindingResult results, HttpServletRequest request) throws Exception {
		
		if (results.hasErrors()) {
			getUiMessageHelper().errors(request, results.getAllErrors());
			return new ModelAndView(String.format("redirect:/application/new"));
		}
		
		User user = SecurityHelper.getLoggedInUser();
		
		command.setAccount(user.getAccount());
		
		Group group = getGroupDAO().findByName("application");
		
		command.getGroups().add(group);
		
		//getUserDAO().saveAndEncode(command);
		
		String[] random = getRandomService().generateStrings(2, 16);
		
		String token = DigestUtils.shaHex(String.format("%s%s%s%s%s", command.getFirstname(), user.getAccount().getId(), user.getAccount().getCreatedOn(),  new Date(), random[0]));
		
		//String secretKey = DigestUtils.shaHex(String.format("%s%s%s", token, new Date(), random));
		
		command.setUsername(token);
		//command.setPassword(secretKey);
		
		getUserDAO().saveAndEncode(command);
		//getUserDAO().update(command);
		
		return new ModelAndView("redirect:/application/"+command.getId()+"/edit");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request) throws Exception {
		
		User user = new User();
		
		ModelAndView model = new ModelAndView("app/app-new");
		model.addObject("command", user);
		
		return model;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}

	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}
}
