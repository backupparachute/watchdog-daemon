package com.kylemiller.watchdogd.service.notification.notifiers;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import com.kylemiller.watchdogd.model.SiteNotification;

public class TwitterNotifier implements Notifier {

	private final Logger log = LoggerFactory.getLogger(TwitterNotifier.class);
	
	@Override
	public void notify(SiteNotification notify) {
		
		if (!notify.isContentReady()) generateSubject(notify);
		
		tweet(notify);
		
	}

	private void tweet(SiteNotification notify) {
		
		try {
		
			log.debug("sending tweet: id="+notify.getId());
//			Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance("LUXHgqocsaIs1xH1H1kUTQ", "e2PRy9TM0UadBCETEjBAKKqPNQbgI1vSyqmN7Wsvo4", new AccessToken("97575627-NMclFwt5RfjSdscxyGNhvEFxsdOEfjQwf45CMyJq1", "xGZJ6OfEyghmMbtY8hh3HKkUWJpsNbYaG3VzGHtKv0"));
			Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance("CNeQZ7osI1iEDqZcn0Jew", "OE7wKLyPINn14bT5osZhNTtZnRAWT0IDjPhzG3m8U", new AccessToken("197559688-JQlEHKJD1kztk1ULfvsqK6hkwAH6I5Yf8uPOVQlJ", "sYDzE4CXZATVv0c9eTRwmGI2EQzXfRWgO29zOI"));
		
			DirectMessage msg = twitter.sendDirectMessage(notify.getContact(), notify.getSubject());
			log.debug("TWEET: direct message sent: "+msg.getId());
			
		} catch (TwitterException e) {
			log.error("error sending Tweet", e);
		}
		
	}

	private void generateSubject(SiteNotification notify) {
		log.debug("generating tweet content: id="+notify.getId());
		StringBuilder sb = new StringBuilder();
		
		String template = "Site: %s is %s.  Reason: %s";
		
		notify.setSubject(String.format("%s : %s : %s %s", notify.getSite().getName(), notify.getSiteReport().getType(), notify.getSiteReport().getStatusCode(), StringUtils.defaultIfEmpty(notify.getSiteReport().getMessage(),"")));
		//notify.setSubject(String.format(template, notify.getSite().getName(), notify.getSiteReport().getType(), notify.getSiteReport().getStatusCode()));
		notify.setContentReady(true);
	}

}
