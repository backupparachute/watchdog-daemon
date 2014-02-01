package com.kylemiller.watchdogd.probe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.protocol.http.HttpCallback;
import com.kylemiller.watchdogd.protocol.http.HttpClientHelper;
import com.kylemiller.watchdogd.protocol.http.HttpResponse;

public class HttpProbe extends BaseProbe {
	private final Logger log = LoggerFactory.getLogger(HttpProbe.class);
	private HttpClientHelper httpClient;
	
	public SiteReport probe(final Site site, final SiteReport report) {
		log.debug(String.format("probing site name: %s, uri: %s", site.getName(), site.getUri()));
		
		httpClient.execute(scrubURI(site.getUri()), new HttpCallback() {
			
			@Override
			public void after(HttpResponse response) {
				report.setStatusCode(response.getStatusCode());
				report.setMessage(response.getStatusMessage());
				report.setType(response.hasError() ? SiteReport.FAILURE_TYPE : SiteReport.SUCCESS_TYPE);
				checkForKeyword(site, report, response.getInputStream());
			}
		});
		
		return report;
	}

//	private void addHttpHeaders(GetMethod get, Site site) {
//		
//		log.debug("added request headers...");
//		for(SiteAttribute attr : site.getAttributes()) {
//			if(StringUtils.equalsIgnoreCase("HTTP-HEADER", attr.getType())) {
//				get.addRequestHeader(attr.getName(), attr.getValue());
//			}
//		}
//	}
//
//	private Credentials generateCredentials(Site site) {
//		SiteCredentials cred = site.getCredentials();
//		Credentials credentials = null;
//		
//		if (null != cred) credentials = new UsernamePasswordCredentials(cred.getUsername(), cred.getPassword());
//		return credentials;
//	}
	
	private String scrubURI(String uri) {
		
		if (StringUtils.startsWithIgnoreCase(uri, "http://") || StringUtils.startsWithIgnoreCase(uri, "https://")) return uri;
		
		return "http://"+uri;
	}

	public HttpClientHelper getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClientHelper httpClient) {
		this.httpClient = httpClient;
	}
	
}
