package com.kylemiller.watchdogd.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class DateHelper {

	private static final String ONLY_DATE = "yyyy-MM-dd";
	private static final String[] ACCEPTED_DATE_FORMATS = new String[] {"yyMMdd", "yyyy-MM-dd", "MM/dd/yy", "MM/dd/yyyy"};
	private static final String DATE_WITH_TIME = "yyyy-MM-dd HH:mm";
	

	public Date daysBeforeMidnight(Integer i) {
		return daysBeforeMidnight(new Date(), i);
	}
	
	public Date daysBeforeMidnight(Date date, Integer i) {
		Date d = DateUtils.addDays(date, i);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        
        return cal.getTime();
	}
	
	public Date parse(String date) {
		try {
			return DateUtils.parseDate(date, ACCEPTED_DATE_FORMATS);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public String dateWithTimeFromEpoch(Long l) {
		return DateFormatUtils.format(fromEpoch(l), DATE_WITH_TIME);
	}
	
	public String dateWithTime(Date date) {
		return DateFormatUtils.format(date, DATE_WITH_TIME);
	}
	
	public String onlyDate(Date date) {
		return DateFormatUtils.format(date, ONLY_DATE);
	}
	
	public Date fromEpoch(long l) {
		return new Date(l * 1000);
	}
	
}
