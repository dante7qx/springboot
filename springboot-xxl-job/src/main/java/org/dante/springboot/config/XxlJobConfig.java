package org.dante.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class XxlJobConfig {
	
	@Autowired
	private XxlJobProperties xxlJobProperties; 

	@Bean
	public XxlJobSpringExecutor xxlJobExecutor() {
	    log.info(">>>>>>>>>>> xxl-job config init.");
	    XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
	    xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
	    xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppname());
	    xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
	    xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
	    xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
	    xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
	    xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());
	    return xxlJobSpringExecutor;
	}
	
}
