package com.kylemiller.watchdogd.probe.async.site;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteQueue;
import com.kylemiller.watchdogd.model.dao.SiteDAO;
import com.kylemiller.watchdogd.model.dao.SiteQueueDAO;

public class SiteQueueServiceImpl {
	private final Logger log = LoggerFactory.getLogger(SiteQueueServiceImpl.class);
	private SiteDAO siteDAO;
	//private BlockingQueue<Site> siteQueue;
	private SiteQueueDAO siteQueueDAO;
	private Integer siteQueueInterval = -5;
	
	public void run() {
	
		List<Site> sites = getSiteDAO().findSitesToMonitor(DateUtils.addMinutes(new Date(), getSiteQueueInterval()));
		log.debug(String.format("found %s sites to queue...", CollectionUtils.size(sites)));
		//getSiteQueue().addAll(sites);
		
		for(Site site : sites) getSiteQueueDAO().save(new SiteQueue(site));
	}
	
	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}

	public SiteQueueDAO getSiteQueueDAO() {
		return siteQueueDAO;
	}

	public void setSiteQueueDAO(SiteQueueDAO siteQueueDAO) {
		this.siteQueueDAO = siteQueueDAO;
	}

	public Integer getSiteQueueInterval() {
		return siteQueueInterval;
	}

	public void setSiteQueueInterval(Integer siteQueueInterval) {
		this.siteQueueInterval = siteQueueInterval;
	}
}
