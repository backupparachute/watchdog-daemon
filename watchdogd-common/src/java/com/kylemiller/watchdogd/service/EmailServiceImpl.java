package com.kylemiller.watchdogd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceImpl implements EmailService {
	
	private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
	private JavaMailSender javaMailSender;
	private SimpleMailMessage emailTemplate;

	//TODO: (Kyle) create email queue to retry failed emails
	public void send(String to, String subject, String body) {
		send(to,null, null, null, subject, body);
	}
	
	public void send(String to, String replyTo, String subject, String body) {
		send(to, null,  replyTo, null, subject, body);
	}
	public void send(String from, List<String> cc, String subject, String body) {
		send(null, from, null, cc, subject, body);
	}
	protected void send(String to, String from, String replyTo, List<String> cc, String subject, String body) {
		SimpleMailMessage msg = new SimpleMailMessage(getEmailTemplate());
		
		if (null != to) msg.setTo(to);
		if (null != from) msg.setFrom(from);
		//TODO: (Kyle) implement CC list functionality
		msg.setSubject(subject);
		if (null != replyTo) msg.setReplyTo(replyTo);
		msg.setText(body);
		
		getJavaMailSender().send(msg);
		log.debug(String.format("EMAIL: sent to: %s, subject: %s", to, subject));
		
	}

	public SimpleMailMessage getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(SimpleMailMessage emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
}
