package com.wipro.ta.samplebank.validator;

import com.wipro.ta.samplebank.auth.Login;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("loginValidator")
public class LoginValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Login.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required", "jdhasjdasd");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "asdasdasda");
		
		
	}
}