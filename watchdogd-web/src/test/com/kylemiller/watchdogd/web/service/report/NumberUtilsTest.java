package com.kylemiller.watchdogd.web.service.report;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;



public class NumberUtilsTest {

	@Test
	public void testUtils() {
		System.out.println(NumberUtils.toInt("-", -7));
	}
}
