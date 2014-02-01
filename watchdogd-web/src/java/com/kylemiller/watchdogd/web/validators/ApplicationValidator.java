package com.kylemiller.watchdogd.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kylemiller.watchdogd.model.User;
import com.kylemiller.watchdogd.web.util.SecurityHelper;

public class ApplicationValidator implements Validator {
	@Override
	public boolean supports(Class cls) {
		return User.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		User user = (User) obj;
		
//		if (isApplication(user)) {
		   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "invalid.application.name.required");
		   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "invalid.application.password.required");
//		}
		
	}

	private boolean isApplication(User user) {
		
		if (SecurityHelper.isUserInGroup(user, "application")) return true;
		
		return false;
	}
}
