package org.dante.springboot.pre;

import java.io.IOException;

import org.dante.springboot.factory.OKHttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import okhttp3.Response;

public class HealthTests {
	
	@Test
	public void testHealth() throws IOException {
		Response response = OKHttpClientUtil.getRestful("/health");
		Assert.assertEquals(200, response.code());
		Assert.assertEquals("up", response.body().string());
	}
}
