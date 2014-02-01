package com.kylemiller.watchdogd.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

@Component("uiMessageHelper")
public class UiMessageHelper {
	
	@Resource
	private MessageSource messageSource;
	
	private static final String UIMESSAGES_KEY = "_uimessages";
	
	public void success(HttpServletRequest request, String msg, String ... params) {
		add(request.getSession(), "success", "", msg, (Object[]) params);
	}
	
	public void warning(HttpServletRequest request, String msg, String ... params) {
		add(request.getSession(), "warning", "", msg, (Object[]) params);
	}
	
	public void error(HttpServletRequest request, String msg, String ... params) {
		add(request.getSession(), "error", "", msg, (Object[]) params);
	}
	
	public void errors(HttpServletRequest request, List<ObjectError> errors) {
		for(ObjectError err : errors) {
			add(request.getSession(), "error", "", err.getCode(), err.getArguments());
		}
	}
	
	protected void add(HttpSession session, String listKey, String key, String msg, Object ... params) {
		List list = findList(session, listKey);
		
		String message = getMessageSource().getMessage(new DefaultMessageSourceResolvable(new String[]{msg}, params ), new Locale("EN_US"));
		list.add(new UIMessage(key, message));
	}

	protected List<UIMessage> findList(HttpSession session, String key) {
		
		Map<String, List<UIMessage>> messages = (Map<String, List<UIMessage>>) session.getAttribute(UIMESSAGES_KEY);
		
		
		if (null == messages) {
			messages = new HashMap();
			session.setAttribute(UIMESSAGES_KEY, messages);
		}
		
		List<UIMessage> list = messages.get(key);
		
		if (null == list) {
			list = new ArrayList();
			messages.put(key, list);
		}
		
		return list;
	}
	
	public class UIMessage {
		private String key;
		private String message;
		
		public UIMessage(String key, String msg) {
			this.key = key;
			this.message = msg;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	

}
