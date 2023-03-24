package org.dante.springboot;

import org.dante.springboot.service.IFlowInstanceService;
import org.dante.springboot.service.IFlowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootCamundaApplicationTests {
	
	@Autowired
	protected IFlowInstanceService flowInstanceService;
	@Autowired
	protected IFlowTaskService flowTaskService;

	/** 张领导 */
	protected final String LEADER_ZHANG = "leader1";
	/** 韩领导 */
	protected final String LEADER_HAN = "leader2";
	
	/** 上级领导 */
	protected final String GROUP_SUPERIOR = "superior";
	/** 主管 */
	protected final String GROUP_MAIN_CHARGE = "mainCharge";
	/** 总管 */
	protected final String GROUP_LEADER = "leader";
	
	/** 测试流程Id */
	protected final String PROC_INS_ID = "d707a772-c7be-11ed-9c32-8216c55d9f18";
	
}
