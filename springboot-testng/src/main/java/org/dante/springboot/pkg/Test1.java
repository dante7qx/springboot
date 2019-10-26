package org.dante.springboot.pkg;

import java.io.IOException;

import org.dante.springboot.factory.OKHttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import okhttp3.Response;

public class Test1 {

	@Test(dependsOnMethods = "testPkgConfig")
	public void testPkg() throws IOException {
		Response response = OKHttpClientUtil.getRestful("/pkg/{test1}");
		Assert.assertEquals(200, response.code());
	}
	
	@Test
	public void testPkgConfig() throws IOException {
		Response response = OKHttpClientUtil.getRestful("/pkg/{config}");
		Assert.assertEquals(200, response.code());
	}

}
