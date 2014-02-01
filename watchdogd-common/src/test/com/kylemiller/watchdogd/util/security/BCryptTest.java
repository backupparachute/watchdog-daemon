package com.kylemiller.watchdogd.util.security;

import org.junit.Test;


public class BCryptTest {

	
	@Test
	public void testpw() {
		System.out.println(BCrypt.hashpw("foobar", BCrypt.gensalt()));
	}
	
}
