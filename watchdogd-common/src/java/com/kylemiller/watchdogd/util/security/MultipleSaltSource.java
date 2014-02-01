package com.kylemiller.watchdogd.util.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class MultipleSaltSource implements SaltSource {

	private List<SaltSource> list = new ArrayList();
	
	public MultipleSaltSource() {
		
		ReflectionSaltSource refl = new ReflectionSaltSource();
		refl.setUserPropertyToUse("username");
		
		SystemWideSaltSource sys = new SystemWideSaltSource();
		sys.setSystemWideSalt("\\bWa9\\-.UAQG&\\VWqFAm[@^$}3RuQ");
		
		list.add(sys);
		list.add(refl);
		
	}
	
	@Override
	public Object getSalt(UserDetails ud) {
		
		StringBuilder sb = new StringBuilder();
		
		for (SaltSource salt : list) {
			sb.append(salt.getSalt(ud)).append("-");
		}
		
		return sb.toString();
	}

}
