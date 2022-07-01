package org.dante.springboot.service.demo;

import java.util.Arrays;
import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

public class MultiSubFlowTests extends SpringbootFlowableDesignApplicationTests {
	
	@Test
	public void startProcessInstance() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_CANDIDATE_USER_LIST.code(), Arrays.asList(new String[] { "lisi", "zhangsan" }));
		this.startProcessInstance("MULTI_SUB_FLOW", variableMap);
	}
	
	@Test
	public void complete() {
		String[] arr = new String[] {"dante"}; 
		for (String currentUserId : arr) {
			String comment = "本皇完成了";
			Map<String, Object> variableMap = Maps.newHashMap();
			variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
			this.complete(currentUserId, comment, variableMap);
		}
		
	}
	
}
