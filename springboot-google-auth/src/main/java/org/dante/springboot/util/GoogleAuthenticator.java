package org.dante.springboot.util;

import java.time.Duration;
import java.time.Instant;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.lang.Console;
import cn.hutool.crypto.digest.otp.HOTP;
import cn.hutool.crypto.digest.otp.TOTP;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GoogleAuthenticator {
	
	
	
	/**
	 * 生成密钥，每个用户独享一份密钥
	 * 
	 * @return
	 */
	public static String getSecretKey() {
		return HOTP.generateSecretKey(20);
	}
	
	
	public static String getQrCodeText(String secretKey, String account) {
		return TOTP.generateGoogleSecretKey(account, 6);
	}
	
	
	public static void main(String[] args) {
		String secretKey = HOTP.generateSecretKey(20);
		TOTP totp = new TOTP(Duration.ofSeconds(30), Base32.decode(secretKey));
		Console.log(secretKey);
		Console.log(TOTP.generateGoogleSecretKey(secretKey, 20));
		
		Instant timestamp = Instant.now();
		int code = totp.generate(timestamp);
		Console.log(code);
		
		Instant now = Instant.now();
		int offsetSize = 1;
		boolean validate1 = totp.validate(now.plusSeconds(10), offsetSize, code);
		boolean validate2 = totp.validate(now.plusSeconds(29), offsetSize, code);
		boolean validate3 = totp.validate(now.plusSeconds(39), offsetSize, code);
		boolean validate4 = totp.validate(now.plusSeconds(40), offsetSize, code);
		boolean validate5 = totp.validate(now.plusSeconds(60), offsetSize, code);
		
		Console.log("验证结果 ==> {}, {}, {}, {}, {}", validate1, validate2, validate3, validate4, validate5);
	}
	
}
