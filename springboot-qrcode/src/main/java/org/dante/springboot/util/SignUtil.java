package org.dante.springboot.util;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class SignUtil {
	
	private static final String algorithm = "HmacSHA1";
	
	public static final String sign(String appSecret, String str) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(appSecret.getBytes("UTF-8"), algorithm);
		Mac mac = Mac.getInstance(algorithm);
		mac.init(secretKeySpec);
		byte[] rawMac = mac.doFinal(str.toString().getBytes("UTF-8"));
		String signStr = Base64.getEncoder().encodeToString(rawMac);
		return signStr;
	}
	
}
