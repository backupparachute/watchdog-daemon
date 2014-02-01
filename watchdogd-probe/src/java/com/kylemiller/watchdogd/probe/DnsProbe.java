package com.kylemiller.watchdogd.probe;

import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteAttribute;
import com.kylemiller.watchdogd.model.SiteAttributeKey;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.StatusCode;

public class DnsProbe extends BaseProbe {
	private final Logger log = LoggerFactory.getLogger(DnsProbe.class);
	private String dnsServer = "8.8.8.8";
	
	public SiteReport probe(Site site, SiteReport report) {
		log.debug(String.format("DNS PROBE site name: %s, uri: %s", site.getName(), site.getUri()));
		try {
		
			Resolver res = new SimpleResolver(getDnsServer());
			res.setTimeout(12000);
			
			Lookup lookup = new Lookup(site.getUri());
			lookup.setResolver(res);
			lookup.setCache(null);
			
			Record[] recs = lookup.run();
			
//			if (lookup.getResult() == Lookup.HOST_NOT_FOUND) {
//				report.setType(SiteReport.FAILURE_TYPE);
//				report.setStatusCode(StatusCode.NOT_FOUND.getCode());
//				report.setMessage(String.format("could not find DNS record"));
//				return report;
//			}
			
			if (lookup.getResult() != Lookup.SUCCESSFUL || null == recs || recs.length == 0) {
				report.setType(SiteReport.FAILURE_TYPE);
				report.setStatusCode(StatusCode.NOT_FOUND.getCode());
				report.setMessage(String.format("could not find DNS record"));
				return report;
			}
			
			ARecord rec = ((ARecord)recs[0]);
			report.setType(SiteReport.SUCCESS_TYPE);
			report.setStatusCode(StatusCode.OK.getCode());
			report.setMessage(StatusCode.OK.getMessage());
			
			SiteAttribute attr = site.findAttribute(SiteAttributeKey.IP_ADDRESS);
			if (null == attr || StringUtils.isBlank(attr.getValue())) {
				report.setType(SiteReport.FAILURE_TYPE);
				report.setStatusCode(StatusCode.IMPROPER_SETUP.getCode());
				report.setMessage(String.format("You must define an IP Address to match"));
				return report;
			}
			
			String addr = rec.getAddress().getHostAddress();
			
			if (!StringUtils.equalsIgnoreCase(addr, attr.getValue())) {
				report.setType(SiteReport.FAILURE_TYPE);
				report.setStatusCode(StatusCode.KEYWORD_NOT_FOUND.getCode());
				report.setMessage(String.format("could not find %s in DNS record", attr.getValue()));
				return report;
			}
				
		} catch (Exception e) {
			log.warn(String.format("site %s problem",site.toString()), e);
			report.setType(SiteReport.FAILURE_TYPE);
			report.setMessage(e.getMessage());
			report.setStatusCode(StatusCode.ERROR.getCode());
			
//			 UnknownHostException, TextParseException
			
			if (e instanceof UnknownHostException) {
				report.setStatusCode(StatusCode.NOT_FOUND.getCode());
			}
		} 
		
		return report;
	}

	public String getDnsServer() {
		return dnsServer;
	}

	public void setDnsServer(String dnsServer) {
		this.dnsServer = dnsServer;
	}
	
}
