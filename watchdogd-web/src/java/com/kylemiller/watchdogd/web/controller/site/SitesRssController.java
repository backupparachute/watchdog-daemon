package com.kylemiller.watchdogd.web.controller.site;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.util.UriGenerator;
import com.kylemiller.watchdogd.web.util.SecurityHelper;
import com.kylemiller.watchdogd.web.util.SiteStatusImageFactory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

@Controller
public class SitesRssController {

	//@Autowired
	@Resource
	private SiteDAO siteDAO;
	@Resource
	private UriGenerator uriGenerator;
	@Resource
	private SiteStatusImageFactory siteStatusImageFactory;
	
	@RequestMapping(value="/sites.xml")
	public ResponseEntity<String> handle() throws IOException, FeedException {
		
		User user = SecurityHelper.getLoggedInUser();
		List<Site> sites = getSiteDAO().findAllSites(user.getAccount());
		
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("WatchdogDaemon :: Sites Status :: RSS");
		feed.setLink(getUriGenerator().generate("/sites"));
		feed.setDescription("site statuses");
		
		List list = new ArrayList();
		
		for (Site site : sites) {
			SyndEntry entry = new SyndEntryImpl();
			//entry.put("id", site.getId());
			entry.setTitle(String.format("%s is %s", site.getName(), site.getStatus().getStatus()));
			entry.setLink(getUriGenerator().generate("/site/"+site.getId()));
			entry.setPublishedDate(site.getStatus().getLastConnected());
			SyndContent content = new SyndContentImpl();
			content.setType("text/html");
			content.setValue(String.format("<img src=\"%s\" />",getUriGenerator().generate("/images/"+getSiteStatusImageFactory().find(site))));
			entry.setDescription(content);
			list.add(entry);
		}
		
		feed.setEntries(list);
		
		StringWriter sw = new StringWriter();
//		return list.toJSONString();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/rss+xml");
		
		SyndFeedOutput output = new SyndFeedOutput();
		output.output(feed,sw);
		sw.flush();
		
		ResponseEntity<String> entity = new ResponseEntity<String>(sw.toString(), headers, HttpStatus.OK);
		
		return entity;
	}
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}
	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}
	public UriGenerator getUriGenerator() {
		return uriGenerator;
	}
	public void setUriGenerator(UriGenerator uriGenerator) {
		this.uriGenerator = uriGenerator;
	}
	public SiteStatusImageFactory getSiteStatusImageFactory() {
		return siteStatusImageFactory;
	}
	public void setSiteStatusImageFactory(
			SiteStatusImageFactory siteStatusImageFactory) {
		this.siteStatusImageFactory = siteStatusImageFactory;
	}
}
