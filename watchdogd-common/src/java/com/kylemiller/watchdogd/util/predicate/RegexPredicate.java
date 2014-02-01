package com.kylemiller.watchdogd.util.predicate;

import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;

public class RegexPredicate implements Predicate {

	private Pattern regex = null;
	private String propertyName = null;
	public RegexPredicate(String regexPattern) {
		this(regexPattern, null);
	}
	public RegexPredicate(String regex, String propertyName) {
		this.regex = Pattern.compile(regex);
		this.propertyName = propertyName;
	}
	
	public boolean evaluate(Object obj) {
		if (null == obj) return false;
		
		if (null != propertyName) {
			try {
				obj = PropertyUtils.getProperty(obj, propertyName);
			} catch (Exception e) {
				return false;
			}
		}
		
		return regex.matcher(obj.toString()).matches();
	}

}
