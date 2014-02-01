package com.kylemiller.watchdogd.web.controller.webhooks;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kylemiller.watchdogd.service.EmailService;
import com.kylemiller.watchdogd.web.util.Config;
import com.kylemiller.watchdogd.web.webhooks.handlers.WebhookHandler;
import com.kylemiller.watchdogd.web.webhooks.handlers.WebhookHandlerFactory;
import com.stripe.model.EventData;
import com.stripe.model.EventDataDeserializer;

@Controller
public class WebhooksController {
	
	private final Logger log = LoggerFactory.getLogger(WebhooksController.class);
	@Resource
	private Config config;
	@Resource
	private WebhookHandlerFactory webhookHandlerFactory;
	@Resource
	private EmailService emailService;
	
	@RequestMapping(value="/webhooks",method=RequestMethod.POST)
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String json = IOUtils.toString(request.getInputStream());
		
		log.debug(String.format("webhook received: %s",json));
		
		JsonObject obj = (JsonObject) new JsonParser().parse(json);
		
		String type = obj.get("type").getAsString();
		
		try {
			WebhookHandler handler = getWebhookHandlerFactory().getHandler(type);
			EventData data = new EventDataDeserializer().deserialize(obj.get("data"), null, null);
			if (null != handler && !handler.handle(data.getObject())) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
		} catch (NoSuchBeanDefinitionException e) {
			log.warn(String.format("no webhook handler found for type %s", type));
			getEmailService().send("webhooks@watchdogdaemon.com", "[WatchdogDaemon] webhook: "+type, obj.toString());
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
		
		return;
	}
	@RequestMapping(value="/webhooks",method=RequestMethod.GET)
	public ModelAndView view() throws Exception {
		return new ModelAndView("admin/webhooks");
	}
	
	public void setConfig(Config config) {
		this.config = config;
	}
	public Config getConfig() {
		return config;
	}


	public WebhookHandlerFactory getWebhookHandlerFactory() {
		return webhookHandlerFactory;
	}

	public void setWebhookHandlerFactory(WebhookHandlerFactory webhookHandlerFactory) {
		this.webhookHandlerFactory = webhookHandlerFactory;
	}
	public EmailService getEmailService() {
		return emailService;
	}
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
}
