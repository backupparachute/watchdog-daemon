package com.kylemiller.watchdogd.service;

import java.util.List;

public interface EmailService {

	public abstract void send(String to, String subject, String body);
	public void send(String to, String replyTo, String subject, String body);
	public void send(String from, List<String> cc, String subject, String body);
}