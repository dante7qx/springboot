package org.dante.springboot.pkg;

import java.io.IOException;

import org.dante.springboot.factory.OKHttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import okhttp3.Response;

public class Test2 {

	/**
	 * “超时”表示如果单元测试花费的时间超过指定的毫秒数，那么TestNG将会中止它并将其标记为失败。
	 * 
	 * @throws IOException
	 */
	@Test(timeOut = 3000)
	public void testInPkg() throws IOException {
		Response response = OKHttpClientUtil.getRestful("/pkg/{test2}");
		Assert.assertEquals(200, response.code());
	}
	
	@Test(expectedExceptions = ArithmeticException.class)
    public void divisionWithException() {
        int i = 1 / 0;
        System.out.println("After division the value of i is :"+ i);
    }

}
