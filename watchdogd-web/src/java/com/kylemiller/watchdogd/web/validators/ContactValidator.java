package com.kylemiller.watchdogd.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kylemiller.watchdogd.model.Contact;

public class ContactValidator implements Validator {
	@Override
	public boolean supports(Class cls) {
		return Contact.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Contact contact = (Contact) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "invalid.contact.name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "invalid.contact.value.required");
	}
}
