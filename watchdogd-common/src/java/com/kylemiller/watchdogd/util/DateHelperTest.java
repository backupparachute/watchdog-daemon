package com.kylemiller.watchdogd.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class DateHelperTest {

	DateHelper dateHelper;
	Long epoch = new Long(1340977444);
	
	@Before
	public void setup() {
		dateHelper = new DateHelper();
	}
	
	@Test public void testDaysBeforeMidnight() {
		Date date = new Date(epoch*1000);
		
		date = dateHelper.daysBeforeMidnight(date, 365);
		
		assertEquals("2013-06-29 00:00", dateHelper.dateWithTime(date));
	}
	
	@Test
	public void testDateWithTime() {
		
		Date date = new Date(epoch*1000);
		String val = dateHelper.dateWithTime(date);
		
		assertEquals("2012-06-29 08:44", val);
	}
	@Test
	public void testDateFromEpoch() {
		Long epoch = new Long(1340977444);
		
		String date = dateHelper.dateWithTimeFromEpoch(epoch);
		
		assertEquals("2012-06-29 08:44", date);
	}
	
}
