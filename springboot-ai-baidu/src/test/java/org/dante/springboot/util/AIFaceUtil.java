package org.dante.springboot.util;

import org.junit.jupiter.api.Test;

import com.baidu.aip.face.AipFace;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AIFaceUtil {

	@Test
	public void getAccessToken() {
		final String APP_ID = "26257658";
		final String API_KEY = "MNm2dB0PDf3zG8RgSmiPORar";
		final String SECRET_KEY = "ETExN1HdeXUABzU1OnGKxtMjjoPscZXl";
		
		AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
		log.info("{}", client.toString());
	}
	
}
