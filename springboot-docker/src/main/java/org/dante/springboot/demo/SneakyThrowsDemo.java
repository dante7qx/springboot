package org.dante.springboot.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.SneakyThrows;

public class SneakyThrowsDemo {
	
	@SneakyThrows
	public void a1() {
		b();
	}
	
	public void a2() throws IOException {
		b();
	}
	
	public void a3() {
		try {
			b();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void b() throws IOException {
		
	}
	
	public static void main(String[] args) {
		String initKey = "LmMGStGtOpF4xNyvYt54EQ==";
		
		String decodeStr = Base64.decodeStr(initKey, StandardCharsets.ISO_8859_1);
		Console.log(decodeStr);
		
		String key = decodeStr.substring(0, 8);
		String iv = decodeStr.substring(8);
		Console.log(key.length());
		Console.log(iv.length());
		
		
		String encryptedText = "J224J7K/ExubpUcddGm+YQJjlH2dweQuVBbgyHGvvacbqrNPwbqHwFlBqfOU6F23gSThO+e5QHQGPFvH5JYSwC8FlzZq3bqnsJOKiFkGv7QsAUZFDIcxaCupzs0IvMm/0az11Y12po3XCf0qT2/ycnaXxy5wvVV6gIcVK9ehAml0l/CoLH7wN2YcjZB7FQvnapmvfLKn+v2CNJZTN+0/etJUNOXDpQHqCgGffcc2SIZubUHxsZ1dUkHbWyjYyva6si7L2uRUg/giOMPSfEfLOiME5RgSJvMgICHdwoAWJxXZ+nbUmEzQ4w5zBbKcFpHMISWPdXxSxXmNB0ctwGz8n9gYZYhEStQEicfdOFj1cnI4Jjp5F6iwvfCKlTrRnEXUqMFSXpf95KWCmqfruREZCQrR2g0wKf92ujLIjaeU23DiN6ks3q3kE0A5mRHHu5UAfu+d9qiytBnjLFfbuRr0hobxZimjXq10iIZTDdGPtsPnekjQYfTxuVPyXMulTNVMqKygQMX0IROyG6+xz7qs8AlQ37PDXkGtj0eMiQM7kI/BE37dwtngmSYkCXqhuvKSrF3C10KoXBegzvYUaiDrUtjUf4chx2vmyXFmgi+2e+8Uf/P6kCObKnPIleJAi49zQ1NmaqQN8ruP3SCWIopDZqm1nfHIrckO1CdvcI7D1yQfHlI+GrVEH3Rq6jJp20vvxNTB05XEK/C610l2SmPxrYgTzAVUxbURLRnBSC1XPvEakgBUltTTQ0VEb0oj60/ZO6+mLbs1SpdUf4O//apVGdabbnTynNwEzEtlLD6JrdSG9njuLnMyuMLTn8A8bicAyea12ap3qpl4+0HUKrCpKCC5VQ5O7zsgTJDwKDopE7u/h/lAZG0FXp95NDDerTev+Q7CfoZawO7PtmJ7Xqv+7h9ROe6zWka21i83dB6uxwRXA0er8x6H07gBFHLukctIZ08PBjLePdWc8oxO1+gZkxEeKPhVbAHYcPqvNAmt0iCKonuYWv6WiK2v/k8TAMz6evCyaaeR3DDXj8uTZJPtEDIfJ+e4aDaPVGfE+MhSg8s=";
		byte[] encryptedBytes = Base64.decode(encryptedText);
		SymmetricCrypto crypto = new SymmetricCrypto("DES/CBC/PKCS5Padding", key.getBytes(StandardCharsets.ISO_8859_1));
		crypto.setIv(iv.getBytes(StandardCharsets.ISO_8859_1));
		
		byte[] decryptedBytes = crypto.decrypt(encryptedBytes);
		String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
		Console.log("Decrypted Text: " + decryptedText);
		String result = "{" + StrUtil.subAfter(decryptedText, '{', false);
		Console.log(result);
		
	}
	
}
