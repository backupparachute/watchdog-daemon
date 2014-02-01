package com.kylemiller.watchdogd.web.controller.admin.twitter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;


@Controller
public class TwitterController {
	
	
	@RequestMapping(value="/admin/twitter",method=RequestMethod.GET)
	protected ModelAndView show(HttpSession session) throws Exception {
		
		Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance("CNeQZ7osI1iEDqZcn0Jew", "OE7wKLyPINn14bT5osZhNTtZnRAWT0IDjPhzG3m8U");

		    RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:8080/admin/twitter/callback");

		    session.setAttribute("requestToken_token", requestToken.getToken());
		    session.setAttribute("requestToken_secret", requestToken.getTokenSecret());
		    Map model = new HashMap();
		    model.put("uri", requestToken.getAuthorizationURL());
		
		return new ModelAndView("twitter/auth", model);

		}	

	
	@RequestMapping(value="/admin/twitter/callback",method=RequestMethod.GET)
	public ModelAndView view(HttpSession session, @RequestParam("oauth_verifier") String oauthVerifier) throws Exception {
		
//		
		Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance("CNeQZ7osI1iEDqZcn0Jew", "OE7wKLyPINn14bT5osZhNTtZnRAWT0IDjPhzG3m8U");
		
		AccessToken token = twitter.getOAuthAccessToken(new RequestToken((String) session.getAttribute("requestToken_token"), (String) session.getAttribute("requestToken_secret")), oauthVerifier);
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("token", token.getToken());
		mv.addObject("secretKey", token.getTokenSecret());
		mv.addObject("screenName", token.getScreenName());
		mv.addObject("userId", token.getUserId());
		
		mv.setViewName("twitter/callback");
		
		
		twitter.updateStatus("oauth with twitter4j...");
		
		
		return mv;
		
	}

}
