package com.kylemiller.watchdogd.probe;

import java.net.SocketTimeoutException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteAttribute;
import com.kylemiller.watchdogd.model.SiteAttributeKey;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.StatusCode;

public class FtpProbe extends BaseProbe {
	private final Logger log = LoggerFactory.getLogger(FtpProbe.class);
	
	public SiteReport probe(Site site, SiteReport report) {
		log.debug(String.format("FTP PROBE site name: %s, uri: %s", site.getName(), site.getUri()));
		try {
		
			FTPClient ftp = new FTPClient();
		     int reply;
		     if (null != site.getPort() && site.getPort() > 0) {
		    	 ftp.connect(site.getUri(), site.getPort());
		     } else {
		    	 ftp.connect(site.getUri());
		     }
		     
				SiteAttribute username = site.findAttribute(SiteAttributeKey.FTP_USERNAME);
				SiteAttribute password = site.findAttribute(SiteAttributeKey.FTP_PASSWORD);
								
		      reply = ftp.getReplyCode();

		      if(!FTPReply.isPositiveCompletion(reply)) {
		        ftp.disconnect();
		        report.setType(SiteReport.FAILURE_TYPE);
		        report.setStatusCode(StatusCode.COULD_NOT_CONNECT.getCode());
		        report.setMessage(StatusCode.COULD_NOT_CONNECT.getMessage());
		        return report;
		      }
		      
		      if ((null != username && StringUtils.isNotBlank(username.getValue())) && null != password ) {
			      if (!ftp.login(username.getValue(), password.getValue())) {
			    	  report.setType(SiteReport.FAILURE_TYPE);
			    	  report.setStatusCode(StatusCode.FORBIDDEN.getCode());
			    	  report.setMessage(StatusCode.FORBIDDEN.getMessage());
			    	  return report;
			      }
		      }
		      
		      if (!ftp.sendNoOp()) {
		    	  report.setType(SiteReport.FAILURE_TYPE);
		    	  report.setStatusCode(StatusCode.ERROR.getCode());
		    	  report.setMessage("NOOP command failed");
		    	  return report;
		      }
		      
		      report.setType(SiteReport.SUCCESS_TYPE);
		      report.setStatusCode(StatusCode.OK.getCode());
		      report.setMessage(StatusCode.OK.getMessage());
		      
		      ftp.logout();
			
		} catch (Exception e) {
			log.warn(String.format("site %s problem",site.toString()), e);
			report.setType(SiteReport.FAILURE_TYPE);
			report.setMessage(e.getMessage());
			report.setStatusCode(StatusCode.ERROR.getCode());
			
			if (e instanceof SocketTimeoutException) {
				report.setStatusCode(StatusCode.TIMEOUT.getCode());
			}
		} 
		
		return report;
	}
	
}
