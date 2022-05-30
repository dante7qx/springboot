package org.dante.springboot.service.demo;

import java.util.Arrays;
import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

public class MultiTaskSeqFlowTests extends SpringbootFlowableDesignApplicationTests {

	@Test
	public void startProcessInstance() {
		Map<String,Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_CANDIDATE_USER_LIST.code(), Arrays.asList(new String[]{"lisi", "zhangsan"}));
		this.startProcessInstance("MULTI_TASK_SEQ_FLOW", variableMap);
	}
	
	@Test
	public void complete1() {
		String currentUserId = "zhangsan";
		this.complete(currentUserId, "本王完成了");
	}
	
	@Test
	public void complete2() {
		String currentUserId = "lisi";
		this.complete(currentUserId, "本皇完成了");
	}
}
