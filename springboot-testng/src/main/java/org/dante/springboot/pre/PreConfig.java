package org.dante.springboot.pre;

import org.dante.springboot.factory.OKHttpClientUtil;
import org.testng.annotations.BeforeSuite;
import org.testng.log4testng.Logger;

public class PreConfig {
	
	private static final Logger LOGGER = Logger.getLogger(PreConfig.class);
	private static final String SERVERURL = "http://localhost:7901";
	
	@BeforeSuite
	public void buildServerUrl() {
		LOGGER.info(SERVERURL);
		OKHttpClientUtil.buildServerUrl(SERVERURL);
	}
}
