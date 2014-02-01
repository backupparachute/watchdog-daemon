package com.kylemiller.watchdogd.probe;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.StatusCode;

public class TcpProbe extends BaseProbe {
	private final Logger log = LoggerFactory.getLogger(TcpProbe.class);
	
	public SiteReport probe(Site site, SiteReport report) {
		log.debug(String.format("TCP PROBE site name: %s, uri: %s", site.getName(), site.getUri()));
		try {
			
			Socket socket = new Socket();
			InetSocketAddress addr = new InetSocketAddress(site.getUri(),site.getPort());
			socket.connect(addr, 12000);
			
			if (socket.isConnected()) {
				report.setStatusCode(StatusCode.OK.getCode());
				report.setType(SiteReport.SUCCESS_TYPE);
			} else {
				report.setStatusCode(StatusCode.ERROR.getCode());
				report.setType(SiteReport.FAILURE_TYPE);
			}
			
//			checkForKeyword(site, report, socket.getInputStream());
			
			try {
				socket.close();
			} catch (Exception e) {
				//ignore
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
