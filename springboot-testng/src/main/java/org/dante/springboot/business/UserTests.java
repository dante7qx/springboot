package org.dante.springboot.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dante.springboot.factory.OKHttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import okhttp3.Response;

public class UserTests {
	
	private static final Logger LOGGER = Logger.getLogger(UserTests.class);
	
	@Test(priority = 1)
	@Parameters("arguments")
	public void testPostMsg(String args) throws IOException {
		LOGGER.info("Req /user/p-msg".concat(args));
		Map<String, Object> param = new HashMap<>();
		param.put("msgId", "12345");
		param.put("msg", args);
		Response response = OKHttpClientUtil.postRestful("/user/p-msg", param);
		Assert.assertEquals(200, response.code());
		Assert.assertTrue(response.body().string().contains(args));
	}
	
	/**
	@Test(priority = 2)
	public void testGetMsg() throws IOException {
		String param = "ccb";
		Response response = OKHttpClientUtil.getRestful("/user/g-msg/".concat(param));
		Assert.assertEquals(200, response.code());
		Assert.assertTrue(response.body().string().endsWith(param));
	}

	@Test(priority = 3)
	public void testDeleteMsg() throws IOException {
		String param = "ccb";
		Response response = OKHttpClientUtil.deleteRestful("/user/d-msg/".concat(param));
		Assert.assertEquals(200, response.code());
		Assert.assertTrue(response.body().string().endsWith(param));
	}
	**/

}
