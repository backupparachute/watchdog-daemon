package com.kylemiller.watchdogd.web.controller.account;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.AccountStatus;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.AccountDAO;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.web.util.Config;
import com.kylemiller.watchdogd.web.util.SecurityHelper;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;

@Controller
public class AccountPaymentController {
	
	private final Logger log = LoggerFactory.getLogger(AccountPaymentController.class);
	@Resource
	private AccountDAO accountDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private UiMessageHelper uiMessageHelper;
	@Resource
	private Config config;
	
	@RequestMapping(value="/account/payment",method=RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request) throws Exception {
		
		Stripe.apiKey = getConfig().getApiKey();
		Map<String, Object> customerParams = new HashMap<String, Object>();
		
		String coupon = request.getParameter("coupon");
		
		if (StringUtils.isNotBlank(coupon)) {
			try {
				Coupon.retrieve(coupon);
				customerParams.put("coupon", coupon);
			} catch (Exception e) {
				getUiMessageHelper().error(request, "invalid.coupon.code", coupon);
				return new ModelAndView("redirect:/account/payment");
			}
		}
		
		
		User user = SecurityHelper.getLoggedInUser();
		user = getUserDAO().findById(user.getId());
		
		log.info(String.format("updating customer payment info: %s",user.getUsername()));
		
		Customer cust = Customer.retrieve(user.getAccount().getToken());
		
		AccountStatus status = customerStatus(cust);
		
		customerParams.put("card", request.getParameter("stripeToken"));
		
		cust.update(customerParams);
		
		if (customerStatus(cust).equals(AccountStatus.INACTIVE)) {
			customerParams = new HashMap<String, Object>();
			customerParams.put("plan", getConfig().getSubscription());
			customerParams.put("prorate", false);
			cust.updateSubscription(customerParams);
		}
		
		log.info(String.format("finished updating customer payment info: %s", user.getUsername()));
		
		getUiMessageHelper().success(request, "success.credit.card.updated");
		return new ModelAndView("redirect:/account/payment");
	}
	

	@RequestMapping(value="/account/payment",method=RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request) throws Exception {
		
		User user = SecurityHelper.getLoggedInUser();
		Stripe.apiKey = getConfig().getApiKey();
		
		user = getUserDAO().findById(user.getId());
		
		String token = user.getAccount().getToken();
		
		if (StringUtils.isBlank(token)) {
			getUiMessageHelper().warning(request, "warning.no.payment");
			return new ModelAndView("redirect:/home");
		}
		
		Customer cust = Customer.retrieve(token);
		
		ModelAndView model = new ModelAndView("account/payment");
		model.addObject("command", user);
		model.addObject("card", cust.getActiveCard());
		Map map = new HashMap();
		map.put("customer", user.getAccount().getToken());
		ChargeCollection cc = Charge.all(map);
		model.addObject("charges", cc.getData());
		model.addObject("subscription", cust.getSubscription());
		model.addObject("nextCharge", cust.getNextRecurringCharge());
		model.addObject("customer", cust);
		
		AccountStatus status = customerStatus(cust);
		model.addObject("status", status.toString());
		
		if (AccountStatus.INACTIVE.equals(status)) {
			model.addObject("plan", Plan.retrieve(getConfig().getSubscription()));
		}
		return model;
	}
	
	
	private AccountStatus customerStatus(Customer cust) {
		
		Subscription sub = cust.getSubscription();
		String status = "active trialing";
		
		if(null == sub) { 
//		if(null == sub || !StringUtils.containsIgnoreCase(status, sub.getStatus())) {
			return AccountStatus.INACTIVE;
//		} else if (sub.getCancelAtPeriodEnd()) { //TODO (kyle) refactor when new stripe.com api is released
		} else if (null != sub.getCanceledAt() && StringUtils.containsIgnoreCase(status, sub.getStatus())) {
			return AccountStatus.CANCELED;
		} else if (!StringUtils.containsIgnoreCase(status, sub.getStatus())) {
			return AccountStatus.INACTIVE;
		}
		
		return AccountStatus.ACTIVE;
	}
	

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
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
