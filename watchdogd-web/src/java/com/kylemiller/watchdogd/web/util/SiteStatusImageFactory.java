package com.kylemiller.watchdogd.web.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.kylemiller.watchdogd.model.Site;
import com.kylemiller.watchdogd.model.SiteReport;
import com.kylemiller.watchdogd.model.SiteStatus;

@Component("siteStatusImageFactory")
public class SiteStatusImageFactory {

	public String find(Site site) {
		
		if (null == site || null == site.getStatus()) return "warning.png";
		
		SiteStatus status = site.getStatus();
		
		if (StringUtils.equalsIgnoreCase(status.getStatus(), SiteReport.SUCCESS_TYPE)) return "checkmark.png";
		else if (StringUtils.equalsIgnoreCase(status.getStatus(), SiteReport.FAILURE_TYPE)) return "error.png";
		else return "pause.png";
		
	}

}
