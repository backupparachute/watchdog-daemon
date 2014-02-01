package com.kylemiller.watchdogd.util.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class BcryptPasswordEncoder implements PasswordEncoder {

	@Override
	public String encodePassword(String pass, Object salt) throws DataAccessException {
		return BCrypt.hashpw(pass, BCrypt.gensalt());
	}

	@Override
	public boolean isPasswordValid(String enc, String pass, Object salt) throws DataAccessException {
		return BCrypt.checkpw(pass, enc);
	}

}
