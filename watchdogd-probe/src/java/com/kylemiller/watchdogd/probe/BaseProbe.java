package com.kylemiller.watchdogd.probe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteAttribute;
import com.kylemiller.watchdogd.model.SiteAttributeKey;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.StatusCode;
import com.kylemiller.watchdogd.model.dao.SiteReportDAO;
import com.kylemiller.watchdogd.probe.service.ProbeCallback;
import com.kylemiller.watchdogd.service.SiteStatusService;
import com.kylemiller.watchdogd.service.notification.NotificationService;

public abstract class BaseProbe implements Probe {
	
	private final Logger log = LoggerFactory.getLogger(BaseProbe.class);
	private SiteReportDAO siteReportDAO;
	private Site site;
	private NotificationService notificationService;
	private SiteStatusService siteStatusService;
	private ProbeCallback callback;
	private Integer maxSize = 1024^2; //~ 1 MB
	
	public void run() {
		SiteReport report = new SiteReport(getSite());
		StopWatch watch = new StopWatch();
		try {
			before(report);
			watch.start();
			report = probe(getSite(), report);
		} finally {
			watch.stop();
			report.setResponseTime(watch.getTime());
			after(report);
			getSiteReportDAO().save(report);
			getSiteStatusService().toggleStatus(report);
		}
	}

	protected void before(SiteReport report) {
		if (null != getCallback()) getCallback().before(report); 
	}
	
	protected void after(SiteReport report) {
		if (null != getCallback()) getCallback().after(report); 
	}

	public abstract SiteReport probe(Site site, SiteReport report);
	
	protected void checkForKeyword(Site site, SiteReport report, InputStream in) {
		SiteAttribute attr = site.findAttribute(SiteAttributeKey.KEYWORD);
		if (null == attr || StringUtils.isBlank(attr.getValue())) return;
		
		SiteAttribute debug = site.findAttribute(SiteAttributeKey.DEBUG);
		log.debug("checking response for keyword...");
		String s = readAvailable(in);
		if (!StringUtils.contains(s, attr.getValue())) {
			report.setStatusCode(StatusCode.KEYWORD_NOT_FOUND.getCode());
			report.setType(SiteReport.FAILURE_TYPE);
			report.setMessage(String.format("could not find '%s' in response body.", attr.getValue()));
			if (null != debug && Boolean.parseBoolean(debug.getValue())) log.info(String.format("could not find '%s' in response body: %s", attr.getValue(), s));
		}
	}

	protected String readAvailable(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Integer size = 0;
		byte[] buffer = new byte[1024];
		try {
			int avail = 0;
			while ((avail = in.available()) > 0 && size <= getMaxSize()) {
				int len = in.read(buffer, 0, avail);
				if (len > 0) out.write(buffer, 0, len);
				size += len;
			}
			//return IOUtils.toString(in);
//			log.debug("keyword output: "+sw.toString());
			return out.toString();
			
		} catch (IOException e) {
		}
		return "";
	}
	
	public SiteReportDAO getSiteReportDAO() {
		return siteReportDAO;
	}

	public void setSiteReportDAO(SiteReportDAO siteReportDAO) {
		this.siteReportDAO = siteReportDAO;
	}
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public SiteStatusService getSiteStatusService() {
		return siteStatusService;
	}

	public void setSiteStatusService(SiteStatusService siteStatusService) {
		this.siteStatusService = siteStatusService;
	}

	public ProbeCallback getCallback() {
		return callback;
	}

	public void setCallback(ProbeCallback callback) {
		this.callback = callback;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
}
