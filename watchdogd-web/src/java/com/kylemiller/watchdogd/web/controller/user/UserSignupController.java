package com.kylemiller.watchdogd.web.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.model.Group;
import com.kylemiller.watchdogd.model.Invite;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.AccountDAO;
import com.kylemiller.watchdogd.model.dao.GroupDAO;
import com.kylemiller.watchdogd.model.dao.InviteDAO;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.util.UriGenerator;
import com.kylemiller.watchdogd.web.util.Config;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;
import com.stripe.Stripe;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;

@Controller
public class UserSignupController {
	
	private final Logger log = LoggerFactory.getLogger(UserSignupController.class);
	@Resource
	private AccountDAO accountDAO;
	@Resource
	private GroupDAO groupDAO;
	@Resource
	private EmailService emailService;
	@Resource
	private UserDAO userDAO;
	@Resource
	private InviteDAO inviteDAO;
	@Resource
	private UriGenerator uriGenerator;
	@Resource
	private UiMessageHelper uiMessageHelper;
	@Resource
	private Config config;
	
	@RequestMapping(value="/signup/{uuid}",method=RequestMethod.POST)
	public ModelAndView save(User command, @PathVariable("uuid") String uuid, HttpServletRequest request) throws Exception {
		Invite invite = getInviteDAO().findByUuid(uuid);
		
		//TODO: (kyle) add error message
		if (null == invite || (null != invite.getInviteeAccountId() && invite.getInviteeAccountId() > 0)) {
			getUiMessageHelper().warning(request, "invalid.invitation.code");
			return new ModelAndView("redirect:/signup");
		}
		
		if (doesUserExist(command, request)) {
			return new ModelAndView("redirect:/signup");
		}
		
		splitName(command);
		Account account = new Account();
		account.addUser(command);
		
		Group group = getGroupDAO().findByName(getConfig().getGroup());
		
		command.getGroups().add(group);
		
		getAccountDAO().save(account);
		getUserDAO().saveAndEncode(command);
		
		invite.setInviteeAccountId(account.getId());
		getInviteDAO().update(invite);
		
		try {
			getEmailService().send(command.getUsername(), "Welcome to WatchdogDaemon", String.format("Welcome,\nplease visit:\n\n%s", getUriGenerator().generate("/login")));
		} catch (Exception e) {
			log.error("error sending email", e);
		}
		
		getUiMessageHelper().success(request, "success.account.created");
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public ModelAndView save(User command, HttpServletRequest request) throws Exception {
		splitName(command);
		
		Stripe.apiKey = getConfig().getApiKey();
		Map<String, Object> customerParams = new HashMap<String, Object>();
		
		String coupon = request.getParameter("coupon");
		
		if (StringUtils.isNotBlank(coupon)) {
			try {
				Coupon.retrieve(coupon);
				customerParams.put("coupon", coupon);
			} catch (Exception e) {
				getUiMessageHelper().error(request, "invalid.coupon.code", coupon);
				return new ModelAndView("redirect:/signup");
			}
		}
		
		if (doesUserExist(command, request)) {
			return new ModelAndView("redirect:/signup");
		}
		
		customerParams.put("card", request.getParameter("stripeToken")); // obtained with stripe.js
		customerParams.put("description", command.getFirstname() + (StringUtils.isNotBlank(command.getLastname()) ? command.getLastname() : ""));
		
		customerParams.put("plan", getConfig().getSubscription());
		customerParams.put("email", command.getUsername());
		Customer cust = Customer.create(customerParams);
		
		Account account = new Account();
		account.addUser(command);
		
		Group group = getGroupDAO().findByName(getConfig().getGroup());
		
		account.setToken(cust.getId());
		
		command.getGroups().add(group);
		
		getAccountDAO().save(account);
		getUserDAO().saveAndEncode(command);
		
		try {
			getEmailService().send(command.getUsername(), "Welcome to WatchdogDaemon", String.format("Welcome,\nplease visit:\n\n%s", getUriGenerator().generate("/login")));
		} catch (Exception e) {
			log.error("error sending email", e);
		}
		
		getUiMessageHelper().success(request, "success.account.created");
		return new ModelAndView("redirect:/login");
	}
	protected boolean doesUserExist(User command, HttpServletRequest request) {
		try {
			User user = getUserDAO().findByUsername(command.getUsername());
			if (null != user) {
				getUiMessageHelper().error(request, "invalid.username.already.exists", command.getUsername());
				return true;
			}
		} catch (EmptyResultDataAccessException e) {
			//no user found, continue
		}
		return false;
	}
	
	private void splitName(User user) {
		String[] s = StringUtils.split(user.getFirstname());
		user.setFirstname(s[0]);
		if (s.length > 1) user.setLastname(s[1]);
	}

	@RequestMapping(value="/signup/{uuid}",method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("uuid") String uuid) throws Exception {
		User user = new User();
		
		ModelAndView model = new ModelAndView("users/signup-nocc");
		model.addObject("command", user);
		
		return model;
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public ModelAndView view() throws Exception {
		
		User user = new User();
		
		ModelAndView model = new ModelAndView("users/signup");
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

	public InviteDAO getInviteDAO() {
		return inviteDAO;
	}

	public void setBetaInviteDAO(InviteDAO inviteDAO) {
		this.inviteDAO = inviteDAO;
	}

	public UriGenerator getUriGenerator() {
		return uriGenerator;
	}

	public void setUriGenerator(UriGenerator uriGenerator) {
		this.uriGenerator = uriGenerator;
	}
	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}
	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
	public Config getConfig() {
		return config;
	}
}
