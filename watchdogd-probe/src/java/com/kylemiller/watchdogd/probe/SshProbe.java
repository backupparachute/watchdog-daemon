package com.kylemiller.watchdogd.probe;

import java.net.SocketTimeoutException;

import net.schmizz.sshj.SSHClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteAttribute;
import com.kylemiller.watchdogd.model.SiteAttributeKey;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.StatusCode;

public class SshProbe extends BaseProbe {
	private final Logger log = LoggerFactory.getLogger(SshProbe.class);
	
	public SiteReport probe(Site site, SiteReport report) {
		log.debug(String.format("SSH PROBE site name: %s, uri: %s", site.getName(), site.getUri()));
		try {
			
		     final SSHClient ssh = new SSHClient();
		     ssh.setConnectTimeout(12000);
		     ssh.setTimeout(12000);

			SiteAttribute host = site.findAttribute(SiteAttributeKey.HOST_KEY);
			SiteAttribute username = site.findAttribute(SiteAttributeKey.USERNAME);
			SiteAttribute password = site.findAttribute(SiteAttributeKey.PASSWORD);
				//if (null == attr || StringUtils.isBlank(attr.getValue())) return;
			if (null == host || null == username || null == password) {
				report.setType(SiteReport.FAILURE_TYPE);
				report.setStatusCode(StatusCode.IMPROPER_SETUP.getCode());
				report.setMessage("You must setup a Host Key, Username and Password.");
				return report;
			}
				
		        //ssh.loadKnownHosts();
		     ssh.addHostKeyVerifier(host.getValue());
		        
		     int port = 22;
		     if (null != site.getPort() && site.getPort() > -1 ) {
		    	 port = site.getPort();
		     }
		     ssh.connect(site.getUri(), port);
		        
		        try {
		            //ssh.authPublickey(System.getProperty("user.name"));
		        	ssh.authPassword("kyle", "P@$$W0rd");
		        	if (ssh.isConnected() && ssh.isAuthenticated()) {
						report.setStatusCode(StatusCode.OK.getCode());
						report.setType(SiteReport.SUCCESS_TYPE);
						report.setMessage(StatusCode.OK.getMessage());
		        	} else {
						report.setType(SiteReport.FAILURE_TYPE);
		        		report.setStatusCode(StatusCode.FORBIDDEN.getCode());
		        		report.setMessage(StatusCode.FORBIDDEN.getMessage());
		        	}
		        	
//		            final Session session = ssh.startSession();
//		            try {
//		                final Command cmd = session.exec("ping -c 1 google.com");
//		                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
//		                cmd.join(5, TimeUnit.SECONDS);
//		                System.out.println("\n** exit status: " + cmd.getExitStatus());
//		            } finally {
//		                session.close();
//		            }
		        	
		        } finally {
		            ssh.disconnect();
		        }
			
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
