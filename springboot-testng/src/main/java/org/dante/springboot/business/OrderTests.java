package org.dante.springboot.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dante.springboot.factory.OKHttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import okhttp3.Response;

public class OrderTests {
	
	@Test(priority = 1)
	public void testPostMsg() throws IOException {
		Map<String, Object> param = new HashMap<>();
		param.put("msgId", "12345");
		param.put("msg", "Hello World");
		Response response = OKHttpClientUtil.postRestful("/order/p-msg", param);
		Assert.assertEquals(200, response.code());
		Assert.assertTrue(response.body().string().contains("Hello World"));
	}
	
	@Test(priority = 2)
	public void testGetMsg() throws IOException {
		String param = "ccb";
		Response response = OKHttpClientUtil.getRestful("/order/g-msg/".concat(param));
		Assert.assertEquals(200, response.code());
		Assert.assertTrue(response.body().string().endsWith(param));
	}

	@Test(priority = 3)
	public void testDeleteMsg() throws IOException {
		String param = "ccb";
		Response response = OKHttpClientUtil.deleteRestful("/order/d-msg/".concat(param));
		Assert.assertEquals(200, response.code());
		Assert.assertTrue(response.body().string().endsWith(param));
	}

}
