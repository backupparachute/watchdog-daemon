package com.kylemiller.watchdogd.web.webhooks.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.backupparachute.jig.Jig;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.service.AccountService;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.util.DateHelper;
import com.stripe.model.Subscription;

@Component
public class CustomerSubscriptionDeletedWebhookHandler implements WebhookHandler<Subscription> {
	
	@Resource
	private EmailService emailService;
	@Resource
	private UserDAO userDAO;
	@Resource
	private Jig subscriptionDeletedEmailJig;
	@Resource
	private Jig subscriptionDeletedSubjectJig;
	@Resource
	private DateHelper dateHelper;
	@Resource
	private AccountService accountService;

	private final Logger log = LoggerFactory.getLogger(CustomerSubscriptionDeletedWebhookHandler.class);
	
	@Override
	public boolean handle(Subscription sub) {
		
		String token = sub.getCustomer();
		
		User user = getUserDAO().findByToken(token);
		
		if (null == user) {
			log.info(String.format("### cust sub deleted webhook received, could not find user: %s",token));
			return true;
		}
		
		getAccountService().disableAccount(token);
		
		Map model = new HashMap();
		model.put("date", getDateHelper().dateWithTimeFromEpoch(sub.getCurrentPeriodEnd()));
		String email = getSubscriptionDeletedEmailJig().render(model);
		getEmailService().send(user.getUsername(), getSubscriptionDeletedSubjectJig().render(model), email);
		
		return true;
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

	public DateHelper getDateHelper() {
		return dateHelper;
	}

	public void setDateHelper(DateHelper dateHelper) {
		this.dateHelper = dateHelper;
	}

	public Jig getSubscriptionDeletedEmailJig() {
		return subscriptionDeletedEmailJig;
	}

	public void setSubscriptionDeletedEmailJig(Jig subscriptionDeletedEmailJig) {
		this.subscriptionDeletedEmailJig = subscriptionDeletedEmailJig;
	}

	public Jig getSubscriptionDeletedSubjectJig() {
		return subscriptionDeletedSubjectJig;
	}

	public void setSubscriptionDeletedSubjectJig(Jig subscriptionDeletedSubjectJig) {
		this.subscriptionDeletedSubjectJig = subscriptionDeletedSubjectJig;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

}
