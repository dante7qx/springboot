package org.dante.springboot.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class EncryptUtil {
	private static final String SITE_WIDE_SECRET = "DANTE-SHIRO";

	private static final PasswordEncoder encoder = new StandardPasswordEncoder(SITE_WIDE_SECRET);
	private static final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	private static final Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

	public static String encrypt(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	public static String bcryptEncrypt(String rawPassword) {
		return bcryptEncoder.encode(rawPassword);
	}

	public static boolean match(String rawPassword, String password) {
		return encoder.matches(rawPassword, password);
	}
	
	public static boolean match2(String rawPassword, String password) {
		return bcryptEncoder.matches(rawPassword, password);
	}

	public static void main(String[] args) {
		String password1 = EncryptUtil.encrypt("1234qwer");
		String password2 = EncryptUtil.encrypt("1qaz2wsx");
		System.out.println(password1);
		System.out.println(password2);
		System.out.println(match("1234qwer", password1));
		System.out.println(match("1qaz2wsx", password2));

		String password3 = bcryptEncoder.encode("1qaz2wsx");
		System.out.println(password3);
		System.out.println(bcryptEncoder.matches("1qaz2wsx", password3));
		System.out.println(bcryptEncoder.matches(password3, password3));
		
		String password4 = pbkdf2PasswordEncoder.encode("1234qwer");
		System.out.println(password4);

		// $2a$10$wPveqTP/r0UUo7PPoUAoT.SdRevbop/uB5UemQvQ.DdixcFcJX3xK
		// $2a$10$lzwaDv8jZCFyZdRBTFQhlOspq/EP3OlxSnyG7nQcgyxjwVgGZ5KPK
	}
}
