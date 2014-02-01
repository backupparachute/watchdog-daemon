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
import com.stripe.model.Charge;

@Component
public class ChargeFailedWebhookHandler implements WebhookHandler<Charge> {
	
	@Resource
	private EmailService emailService;
	@Resource
	private UserDAO userDAO;
	@Resource
	private Jig chargeFailedEmailJig;
	@Resource
	private Jig chargeFailedSubjectJig;
	@Resource
	private DateHelper dateHelper;
	@Resource
	private AccountService accountService;

	private final Logger log = LoggerFactory.getLogger(ChargeFailedWebhookHandler.class);
	
	@Override
	public boolean handle(Charge charge) {
		
		String token = charge.getCustomer();
		
		User user = getUserDAO().findByToken(token);
		
		if (null == user) {
			log.info(String.format("### charge failed webhook received, could not find user: %s",token));
			return true;
		}
		
		getAccountService().disableAccount(token);
		
		Map model = new HashMap();
		String email = getChargeFailedEmailJig().render(model);
		getEmailService().send(user.getUsername(), getChargeFailedSubjectJig().render(model), email);
		
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

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Jig getChargeFailedEmailJig() {
		return chargeFailedEmailJig;
	}

	public void setChargeFailedEmailJig(Jig chargeFailedEmailJig) {
		this.chargeFailedEmailJig = chargeFailedEmailJig;
	}

	public Jig getChargeFailedSubjectJig() {
		return chargeFailedSubjectJig;
	}

	public void setChargeFailedSubjectJig(Jig chargeFailedSubjectJig) {
		this.chargeFailedSubjectJig = chargeFailedSubjectJig;
	}

}
