package com.kylemiller.watchdogd.web.controller.request;

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

import com.kylemiller.watchdogd.model.Request;
import com.kylemiller.watchdogd.model.dao.RequestDAO;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.web.util.UiMessageHelper;


@Controller
@RequestMapping(value="/contact")
public class ContactRequestController {

	private final Logger log = LoggerFactory.getLogger(ContactRequestController.class);
	@Resource
	private RequestDAO requestDAO;
	@Resource
	private UiMessageHelper uiMessageHelper;
	
	@Resource
	private EmailService emailService;
	
//	   @InitBinder
//	public void initBinder(WebDataBinder binder) {
//		   binder.setValidator(new RequestValidator());
//	}
	
	@RequestMapping(method=RequestMethod.GET) 
	public ModelAndView view (@RequestParam(value="s", required=false) String subject) {
		Map map = new HashMap();
		map.put("s", subject);
		return new ModelAndView("support/request", map);
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	public ModelAndView send(@RequestParam(value="_email") String email, @RequestParam(value="_subject") String subject, @RequestParam("_message") String message, @RequestParam("_userId") Integer id, HttpServletRequest request) throws Exception {
		
		Request req = bind(email, subject, message, id);
		boolean hasErrors = validate(req, request);
		
		if (hasErrors) return new ModelAndView(String.format("redirect:/contact"));
		
		getRequestDAO().save(req);
		
		try {
			getEmailService().send("support@watchdogdaemon.com", req.getEmail(), "[WatchdogDaemon] "+req.getSubject(), req.getMessage());
		} catch (Exception e) {
			log.error("error sending support request email", e);
		}
		
		return new ModelAndView("redirect:/");
	}

	private boolean validate(Request req, HttpServletRequest request) {
		
		boolean hasError = false;
		
		if (StringUtils.isBlank(req.getEmail())) {
			getUiMessageHelper().error(request,"invalid.request.email.required");
			hasError = true;
		}
		
		if (StringUtils.isBlank(req.getMessage())) {
			getUiMessageHelper().error(request,"invalid.request.message.required");
			hasError = true;
		}
		
		return hasError;
	}

	protected Request bind(String email, String subject, String message,
			Integer id) {
		Request req = new Request();
		
		req.setEmail(email);
		req.setSubject(subject);
		req.setMessage(message);
		req.setUserId(id);
		return req;
	}

	public RequestDAO getRequestDAO() {
		return requestDAO;
	}

	public void setRequestDAO(RequestDAO requestDAO) {
		this.requestDAO = requestDAO;
	}

	public UiMessageHelper getUiMessageHelper() {
		return uiMessageHelper;
	}

	public void setUiMessageHelper(UiMessageHelper uiMessageHelper) {
		this.uiMessageHelper = uiMessageHelper;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
