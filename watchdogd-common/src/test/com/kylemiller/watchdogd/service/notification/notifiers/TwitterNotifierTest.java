package com.kylemiller.watchdogd.service.notification.notifiers;

import org.junit.Test;

import com.kylemiller.watchdogd.model.SiteNotification;


public class TwitterNotifierTest {
	
	@Test
	public void testTweet() {
		TwitterNotifier notifier = new TwitterNotifier();
		
		SiteNotification note = new SiteNotification();
		note.setContentReady(true);
		
		note.setContact("kyleamiller");
		
		note.setSubject("this is an oauth test tweet");
		
		
		notifier.notify(note);
	}

}
