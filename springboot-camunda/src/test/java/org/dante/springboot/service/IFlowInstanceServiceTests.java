package org.dante.springboot.service;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.dante.springboot.SpringbootCamundaApplicationTests;
import org.dante.springboot.consts.FlowContanst;
import org.dante.springboot.consts.FlowDefKeyContanst;
import org.dante.springboot.vo.StartFlowVO;
import org.junit.jupiter.api.Test;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IFlowInstanceServiceTests extends SpringbootCamundaApplicationTests {
	
	@Test
	public void startLeaveFlow() {
		int days = 2;
		StartFlowVO vo = new StartFlowVO();
		vo.setBizId(IdUtil.fastSimpleUUID());
		vo.setBizUid(IdUtil.fastSimpleUUID());
		vo.addParams(FlowContanst.VAL_APPROVAL, GROUP_SUPERIOR);
		vo.addParams(FlowContanst.VAL_BIZ_UID, vo.getBizUid());
		vo.addParams(FlowContanst.VAL_BIZ_MODEL, "LeaveFlow");
		vo.addParams(FlowContanst.VAL_FLOW_BIZ_DETAIL, "但丁请假" + days + "天，请假原因：年休假。");
		vo.addParams("days", days);
		try {
			ProcessInstance instance = flowInstanceService.startFlowByProcKey(FlowDefKeyContanst.FLOW_LEAVE_KEY, vo);
			log.info("流程实例：{}", instance.getProcessInstanceId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void deleteFlowByProcInsId() {
		flowInstanceService.deleteFlowByProcInsId(PROC_INS_ID);
	}
	
}
