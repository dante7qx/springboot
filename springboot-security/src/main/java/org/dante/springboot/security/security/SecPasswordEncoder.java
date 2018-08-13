package org.dante.springboot.security.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecPasswordEncoder implements PasswordEncoder {
	
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	public SecPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		return bcryptPasswordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if(rawPassword.equals(encodedPassword)) {
			return true;
		} 
		return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
	}

}
