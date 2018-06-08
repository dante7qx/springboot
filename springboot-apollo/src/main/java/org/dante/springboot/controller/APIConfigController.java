package org.dante.springboot.controller;

import org.dante.springboot.prop.DBProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class APIConfigController {

	@Autowired
	private DBProp dbProp;
	
	@Value("${name:Snake}")
	private String appName;
	
	/**
	 * API 方式接入 Apollo
	 * 
	 * @param key
	 * @return
	 */
	@GetMapping("/api/{key}")
	public String getPropByAPIConfig(@PathVariable("key") String key) {
		Config config = ConfigService.getAppConfig();
		
		// 监听配置变化事件
		config.addChangeListener(new ConfigChangeListener() {
		    @Override
		    public void onChange(ConfigChangeEvent changeEvent) {
		    	log.info("Changes for namespace {}", changeEvent.getNamespace());
		        for (String key : changeEvent.changedKeys()) {
		            ConfigChange change = changeEvent.getChange(key);
		            log.info(String.format("Found change - key: {}, oldValue: {}, newValue: {}, changeType: {}", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
		        }
		    }
		});
		
		String val = config.getProperty(key, "未进行配置");
		return val + " - " + config.getProperty("db.name", "数据库名称"); 
	}
	
	@GetMapping("/app")
	public String getLocalConfig() {
		return appName + " - " + dbProp.getName() + " - " + dbProp.getUrl();
	}
	
}
