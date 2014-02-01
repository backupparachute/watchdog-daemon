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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.web.util.Config;
import com.kylemiller.watchdogd.web.util.SecurityHelper;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;
import com.stripe.Stripe;
import com.stripe.model.Customer;


@Controller
@RequestMapping(value="/account/cancel")
public class AccountCancelController {
	
	private final Logger log = LoggerFactory.getLogger(AccountCancelController.class);
	
	@Resource
	private UiMessageHelper messageHelper;
	@Resource
	private Config config;
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value="_delete",required=false) String delete) throws Exception {
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/home");
		
		if (StringUtils.isNotBlank(delete)) {
			
			Account account = SecurityHelper.getLoggedInUser().getAccount();
			
			Stripe.apiKey = getConfig().getApiKey();
			
			Customer cust = Customer.retrieve(account.getToken());
			Map map = new HashMap();
			map.put("at_period_end", Boolean.TRUE);
			cust.cancelSubscription(map);
//			map = new HashMap();
//			map.put("coupon", null);
//			map.put("card", null);
//			cust.update(map);
			
			mv.setViewName("redirect:/account/payment");
		}
		
		return mv;
	}
		
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account/cancel-subscription");
		
		if (StringUtils.isBlank(SecurityHelper.getLoggedInUser().getAccount().getToken())) {
			mv.setViewName("redirect:/home");
			getMessageHelper().warning(request, "warning.no.payment");
		}
		
		return mv;
	}
	
	public void setMessageHelper(UiMessageHelper messageHelper) {
		this.messageHelper = messageHelper;
	}

	public UiMessageHelper getMessageHelper() {
		return messageHelper;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
}
