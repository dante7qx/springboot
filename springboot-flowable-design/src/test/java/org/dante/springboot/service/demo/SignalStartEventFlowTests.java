package org.dante.springboot.service.demo;

import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

/**
 * 参考资料：
 * https://www.cnblogs.com/LQBlog/p/15821015.html
 * 
 * @author dante
 *
 */
public class SignalStartEventFlowTests extends SpringbootFlowableDesignApplicationTests {

	@Test
	public void startProcessInstance() {
		this.startProcessInstance("SIGNAL_START_EVENT_FLOW");
//		runtimeService.signalEventReceived("theSignal");
	}
	
	@Test
	public void complete1() {
		String currentUserId = "zhangsan";
		Map<String,Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
		this.complete(currentUserId, "本王完成了", variableMap);
	}
	
	@Test
	public void complete2() {
		String currentUserId = "lisi";
		this.complete(currentUserId, "本皇完成了");
	}
}
