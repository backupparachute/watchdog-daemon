package com.kylemiller.watchdogd.web.validators;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kylemiller.watchdogd.model.Site;

public class SiteValidator implements Validator {
	@Override
	public boolean supports(Class cls) {
		return Site.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Site site = (Site) obj;
		
		   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "invalid.site.name.required");
		   
		   if (StringUtils.equals("http", site.getType())) {
			   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uri", "invalid.site.uri.required");
		   } else if (StringUtils.equals("tcp", site.getType())) {
			   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uri", "invalid.site.uri.required");
			   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "port", "invalid.site.port.required");
		   }
		   
	}
}
