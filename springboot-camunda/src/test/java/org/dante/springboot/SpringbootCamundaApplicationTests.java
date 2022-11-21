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
	protected final String PROC_INS_ID = "d2d18db0-6967-11ed-8ce9-5ee751a47c30";
	
}
