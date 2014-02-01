package com.kylemiller.watchdogd.probe.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteQueue;
import com.kylemiller.watchdogd.model.dao.SiteQueueDAO;
import com.kylemiller.watchdogd.probe.Probe;

public class ProbeServiceImpl {
	
	private final Logger log = LoggerFactory.getLogger(ProbeServiceImpl.class);
	private ProbeFactory probeFactory;
	private TaskExecutor probeExecutor;
	private BlockingQueue<Site> siteQueue;
	private SiteQueueDAO siteQueueDAO;
	private ProbeCallback probeCallback;
	
	public void run() {
		
		List<SiteQueue> queued = getSiteQueueDAO().findAll(10);
		
		//Site site = null;
		//while (null != (site = getSiteQueue().poll())) {
		for (SiteQueue q : queued) {
			
			Site site = q.getSite();
			
			try {
				getSiteQueueDAO().updateProcessing(q.getId());
				Probe probe = getProbeFactory().findProbe(site);
				if (null != probe) {
					probe.setCallback(getProbeCallback());
					getProbeExecutor().execute(probe);
				}
				
			} catch(Exception e) {
				log.error("error probing site: "+site.toString(), e);
			}
		}
		log.debug("site queue empty...");
	}
	
	public ProbeFactory getProbeFactory() {
		return probeFactory;
	}

	public void setProbeFactory(ProbeFactory probeTypeMatcher) {
		this.probeFactory = probeTypeMatcher;
	}

	public TaskExecutor getProbeExecutor() {
		return probeExecutor;
	}

	public void setProbeExecutor(TaskExecutor probeExecutor) {
		this.probeExecutor = probeExecutor;
	}

	public BlockingQueue<Site> getSiteQueue() {
		return siteQueue;
	}

	public void setSiteQueue(BlockingQueue<Site> siteQueue) {
		this.siteQueue = siteQueue;
	}

	public SiteQueueDAO getSiteQueueDAO() {
		return siteQueueDAO;
	}

	public void setSiteQueueDAO(SiteQueueDAO siteQueueDAO) {
		this.siteQueueDAO = siteQueueDAO;
	}

	public ProbeCallback getProbeCallback() {
		return probeCallback;
	}

	public void setProbeCallback(ProbeCallback probeCallback) {
		this.probeCallback = probeCallback;
	}

	public Logger getLog() {
		return log;
	}

}
