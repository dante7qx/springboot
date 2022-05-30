package org.dante.springboot.service.demo;

import java.util.Arrays;
import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.vo.ArgVO;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

public class MultiTaskParallelFlowTests extends SpringbootFlowableDesignApplicationTests {

	@Test
	public void startProcessInstance() {
		Map<String,Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_CANDIDATE_USER_LIST.code(), Arrays.asList(new String[]{"lisi", "zhangsan"}));
		this.startProcessInstance("MULTI_TASK_PARALLEL_FLOW", variableMap);
	}
	
	@Test
	public void complete1() {
		String currentUserId = "lisi";
		String comment = "本皇完成了";
		this.complete(currentUserId, comment);
	}
	
	@Test
	public void complete2() {
		String currentUserId = "zhangsan";
		String comment = "本王完成了";
		// 多实例动态添加人
		ArgVO arg = new ArgVO("miTasks", "wangwu");
		this.complete(currentUserId, comment, arg);
	}
	
	@Test
	public void complete3() {
		String currentUserId = "wangwu";
		String comment = "小王完成了";
		this.complete(currentUserId, comment);
	}
	
}
