package com.kylemiller.watchdogd.web.webhooks.handlers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.backupparachute.jig.Jig;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.UserDAO;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.util.DateHelper;
import com.stripe.model.Charge;

@Component
public class ChargeSucceededWebhookHandler implements WebhookHandler<Charge> {
	
	@Resource
	private EmailService emailService;
	@Resource
	private UserDAO userDAO;
	@Resource
	private Jig chargeSucceededEmailJig;
	@Resource
	private Jig chargeSucceededSubjectJig;
	@Resource
	private DateHelper dateHelper;

	private final Logger log = LoggerFactory.getLogger(ChargeSucceededWebhookHandler.class);
	
	@Override
	public boolean handle(Charge charge) {
		
		String token = charge.getCustomer();
		
		User user = getUserDAO().findByToken(token);
		
		if (null == user) {
			log.info(String.format("### charge webhook received, could not find user: %s",token));
			return true;
		}
		
		Map model = new HashMap();
		model.put("total", charge.getAmount() / 10);
		model.put("card-type", charge.getCard().getType());
		model.put("card-number", charge.getCard().getLast4());
		Date date = getDateHelper().fromEpoch(charge.getCreated());
		model.put("date", getDateHelper().dateWithTime(date));
		String email = getChargeSucceededEmailJig().render(model);
		getEmailService().send(user.getUsername(), getChargeSucceededSubjectJig().render(model), email);
		
		log.debug("################# web hook: it worked....");
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

	public Jig getChargeSucceededEmailJig() {
		return chargeSucceededEmailJig;
	}

	public Jig getChargeSucceededSubjectJig() {
		return chargeSucceededSubjectJig;
	}

	public void setChargeSucceededSubjectJig(Jig chargeSucceededSubjectJig) {
		this.chargeSucceededSubjectJig = chargeSucceededSubjectJig;
	}

	public void setChargeSucceededEmailJig(Jig chargeSucceededEmailJig) {
		this.chargeSucceededEmailJig = chargeSucceededEmailJig;
	}

	public DateHelper getDateHelper() {
		return dateHelper;
	}

	public void setDateHelper(DateHelper dateHelper) {
		this.dateHelper = dateHelper;
	}

}
