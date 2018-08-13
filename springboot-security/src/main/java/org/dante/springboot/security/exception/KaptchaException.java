package org.dante.springboot.security.exception;

import org.springframework.security.core.AuthenticationException;

public class KaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 7209449153709731409L;

	public KaptchaException(String msg) {
		super(msg);
	}
	
	public KaptchaException(String msg, Throwable t) {
		super(msg, t);
	}

}
