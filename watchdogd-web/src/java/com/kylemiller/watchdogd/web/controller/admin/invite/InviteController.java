package com.kylemiller.watchdogd.web.controller.admin.invite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kylemiller.watchdogd.model.Account;
import com.kylemiller.watchdogd.model.Invite;
import com.kylemiller.watchdogd.model.dao.InviteDAO;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.util.RandomService;
import com.kylemiller.watchdogd.util.UriGenerator;
import com.kylemiller.watchdogd.web.util.SecurityHelper;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;

@Controller
@RequestMapping(value="/admin/invite")
public class InviteController {
	
	private final Logger log = LoggerFactory.getLogger(InviteController.class);
	@Resource
	private InviteDAO InviteDAO;
	@Resource
	private EmailService emailService;
	@Resource
	private RandomService randomService;
	@Resource
	private UriGenerator uriGenerator;
	@Resource
	private UiMessageHelper uiMessageHelper;
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, Invite command) throws Exception {
		
//		Invite invite = getInviteDAO().findByEmail(command.getEmail());
//		
//		if (null != invite) {
//			getUiMessageHelper().warning(request, "warning.invite.already", command.getEmail());
//			return new ModelAndView("redirect:/home");
//		}
		
		Account account = SecurityHelper.getLoggedInUser().getAccount();
		
		command.setInvitorAccountId(account.getId());
		
		String uuid = DigestUtils.shaHex(String.format("%s%s%s", command.getEmail(), new Date(), getRandomService().generateString()));
		
		command.setUuid(uuid);
		
		getInviteDAO().save(command);
		
		send(command, uuid);
		
		getUiMessageHelper().success(request, "success.invite.sent", command.getEmail());
		return new ModelAndView("redirect:/home");
	}

	protected void send(Invite command, String uuid) {
		try {
			String s = String.format("You've been invited to use WatchdogDaemon for FREE.\n\nTo create your account please visit %s\n\n.", getUriGenerator().generate(String.format("/signup/%s",uuid)));
			if (StringUtils.isNotBlank(command.getMessage())) s = String.format("%s\n\n-------------------------\n\n%s",command.getMessage(),s);
			getEmailService().send(command.getEmail(), "WatchdogDaemon invite", s);
		} catch (Exception e) {
			log.error("error sending email", e);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request) throws Exception {
		
		Invite invite = new Invite();
		
		Map map = new HashMap();
		
		map.put("command", invite);
		
		return new ModelAndView("referral/invite", map);
	}

	public InviteDAO getInviteDAO() {
		return InviteDAO;
	}

	public void setInviteDAO(InviteDAO inviteDAO) {
		this.InviteDAO = inviteDAO;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public RandomService getRandomService() {
		return randomService;
	}

	public void setRandomService(RandomService randomService) {
		this.randomService = randomService;
	}

	public UriGenerator getUriGenerator() {
		return uriGenerator;
	}

	public void setUriGenerator(UriGenerator uriGenerator) {
		this.uriGenerator = uriGenerator;
	}
	
	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}

	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}

}
