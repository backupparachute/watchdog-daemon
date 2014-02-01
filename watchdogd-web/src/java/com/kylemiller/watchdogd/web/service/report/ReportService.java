package com.kylemiller.watchdogd.web.service.report;

import java.math.BigDecimal;
import java.util.Date;


public interface ReportService {

	public String generateUptimeReportUrl(Integer id, Date date);
	public String generateCustomValueReportUrl(Integer id, Date date);
	public String generateResponseTimeReportUrl(Integer id, Date date);
	public BigDecimal generateUptimePercent(Integer id, Date date);

}