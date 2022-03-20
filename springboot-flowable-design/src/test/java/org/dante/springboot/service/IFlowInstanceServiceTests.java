package org.dante.springboot.service;

import java.util.UUID;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.enums.LeaveEnum;
import org.dante.springboot.vo.StartFlowInstanceVO;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IFlowInstanceServiceTests extends SpringbootFlowableDesignApplicationTests {
	
	@Autowired
	private IFlowInstanceService flowInstanceService;
	
	private final String startUserId = "zhangsan";
	private final String leaderUserId = "lizhuren";
	
	
	@Test
	public void startProcessInstance() {
		StartFlowInstanceVO vo = new StartFlowInstanceVO();
		vo.setProcessDefKey(FlowEnum.FLOW_DEF_KEY_LEAVE_DELEGATE.code());
		vo.setBussinessKey(UUID.randomUUID().toString().toLowerCase());
		vo.setOperType(FlowEnum.FLOW_OPER_APPL.code());
		vo.setCommentType(LeaveEnum.TJ.code());
		vo.setCommentValue(LeaveEnum.TJ.value());
		vo.setStarterId(startUserId);
		vo.addParams(FlowEnum.FLOW_CANDIDATE.code(), leaderUserId);
		vo.addParams("leaveDays", Integer.valueOf(6));
		try {
			ProcessInstance processInstance = flowInstanceService.startProcessInstance(vo);
			log.info("创建的流程实例：{}", processInstance.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteProcessInstance() throws Exception {
		String processInstanceId = "d7101d0b-a393-11ec-889a-ceba0f5d6d2d";
		flowInstanceService.deleteProcessInstance(processInstanceId);
	}
	
	

}
