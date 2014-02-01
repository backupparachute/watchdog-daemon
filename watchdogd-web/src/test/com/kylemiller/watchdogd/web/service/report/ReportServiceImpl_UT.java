package com.kylemiller.watchdogd.web.service.report;

import org.junit.Test;

import com.kylemiller.watchdogd.web.service.report.ReportServiceImpl;


public class ReportServiceImpl_UT {

	@Test
	public void testInterval() {
		ReportServiceImpl impl = new ReportServiceImpl();
		
		Long interval = impl.determineInterval(4200);
		System.out.println(interval);
	}
	
}
