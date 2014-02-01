package com.kylemiller.watchdogd.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kylemiller.watchdogd.model.Request;

public class RequestValidator implements Validator {
	@Override
	public boolean supports(Class cls) {
		return Request.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Request request = (Request) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid.request.email.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "invalid.message.message.required");
	}
}
