package org.dante.springboot.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.dante.springboot.vo.MsgVO;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QrCodeUtilTests {
	
	private static final String FILE_PATH = "/Users/dante/Documents/Project/java-world/springboot/springboot-qrcode/qrcode/x.jpg";
	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void encodeCode() {
		try {
			String content = mapper.writeValueAsString(new MsgVO(UUID.randomUUID().toString(), "二维码生产", Date.from(Instant.now())));
			OutputStream os = new FileOutputStream(new File(FILE_PATH));
			QrCodeUtil.encode(content, os);
		} catch (Exception e) {
			log.error("QrCodeUtil generate code error.", e);
		}
	}
	
	@Test
	public void decodeCode() {
		try {
			String str = QrCodeUtil.decode(FILE_PATH);
			log.info("str = {}", str);
		} catch (Exception e) {
			log.error("QrCodeUtil decode code error.", e);
		}
	}
	
	@Test
	public void sign() {
		StringBuilder strToSign = new StringBuilder();
		strToSign.append("C-Timestamp:").append(Instant.now().toEpochMilli()).append("\n")
				.append("C-App-Id").append("234567890").append("\n")
				.append("C-Business-Id").append("M1321").append("\n");
		String appSecret = "HA7832IU&W";
		try {
			System.out.println(SignUtil.sign(appSecret, strToSign.toString()));
		} catch (Exception e) {
			log.error("sign error.", e);
		}
		
		
	}
	
}
