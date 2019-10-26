package org.dante.springboot.selenium;

import java.io.IOException;

import org.dante.springboot.factory.OKHttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import okhttp3.Response;

/**
 * Selenium库自动化浏览器来访问网站
 * 
 * @author dante
 */
public class PageLoadTests {
	
	@Test(invocationCount = 20, threadPoolSize = 3)
	public void pressureTest() throws IOException {
		Response response = OKHttpClientUtil.getRestful("/selenium/views");
		Assert.assertEquals(200, response.code());
	}
	
	/**
	@Test(invocationCount = 3)
    public void loadTestThisWebsite() {
//		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
//      WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        WebDriver driver = new FirefoxDriver();  
        driver.get("http://www.yiibai.com");
        System.out.println("Page Title is " + driver.getTitle());
        AssertJUnit.assertEquals("Google", driver.getTitle());
        driver.quit();
    }
	**/
	
}
