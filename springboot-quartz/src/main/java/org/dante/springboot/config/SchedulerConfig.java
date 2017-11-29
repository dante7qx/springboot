package org.dante.springboot.config;

import java.io.IOException;
import java.util.Properties;

import org.dante.springboot.quartz.factory.JobFactory;
import org.dante.springboot.quartz.listener.SpiritJobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {
	
	@Autowired
	@Qualifier("spiritJobFactory")
    private JobFactory jobFactory;
	
	@Bean(name="spiritSchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        return factory;
    }
	
	@Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean();
        propertiesFactory.setLocation(new ClassPathResource("/quartz.properties"));
        return propertiesFactory.getObject();
    }
	
	@Bean
	public SpiritJobListener spiritJobListener() {
		return new SpiritJobListener();
	}
	
}
