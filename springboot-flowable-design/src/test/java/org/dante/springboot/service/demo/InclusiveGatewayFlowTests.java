package org.dante.springboot.service.demo;

import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

public class InclusiveGatewayFlowTests extends SpringbootFlowableDesignApplicationTests {
	
	@Test
	public void startProcessInstance() {
		Map<String,Object> variableMap = Maps.newHashMap();
		variableMap.put("payed", Boolean.TRUE);
		variableMap.put("sended", Boolean.FALSE);
		this.startProcessInstance("INCLUSIVE_GATEWAY_FLOW", variableMap);
	}
	
	@Test
	public void complete1() {
		this.complete("zhangsan", "本王完成了");
	}
	
	@Test
	public void complete2() {
		this.complete("lisi", "本皇完成了");
	}
}
