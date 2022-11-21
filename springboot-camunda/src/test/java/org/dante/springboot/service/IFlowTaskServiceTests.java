package org.dante.springboot.service;

import java.util.List;
import java.util.Map;

import org.dante.springboot.SpringbootCamundaApplicationTests;
import org.dante.springboot.consts.FlowContanst;
import org.dante.springboot.vo.FlowTaskVO;
import org.junit.jupiter.api.Test;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IFlowTaskServiceTests extends SpringbootCamundaApplicationTests {
	
	@Test
	public void todoList() {
		FlowTaskVO vo = new FlowTaskVO();
		vo.setCurUserId(LEADER_HAN);
		List<FlowTaskVO> todoList = flowTaskService.todoList(vo);
		log.info("================================> 待办任务");
		log.info("{}", todoList);
	}
	
	@Test
	public void approval() throws Exception {
		FlowTaskVO query = new FlowTaskVO();
		query.setCurUserId(LEADER_HAN);
		List<FlowTaskVO> todoList = flowTaskService.todoList(query);
		if(CollUtil.isNotEmpty(todoList)) {
			FlowTaskVO vo = todoList.get(0);
			vo.setCurUserId(query.getCurUserId());
			vo.addParams(FlowContanst.VAL_FLOW_AGREE, Boolean.TRUE);
			vo.setComment("同意");
			flowTaskService.approval(vo);
		}
	}
	
	@Test
	public void getFlowVariable() {
		try {
			String val = flowTaskService.getFlowVariable(PROC_INS_ID, FlowContanst.VAL_FLOW_BIZ_DETAIL);
			log.info("Variable -> {}", val);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void getFlowVariables() throws Exception {
		Map<String, Object> flowVariables = flowTaskService.getFlowVariables(PROC_INS_ID, FlowContanst.VAL_BIZ_UID, FlowContanst.VAL_BIZ_MODEL, FlowContanst.VAL_FLOW_BIZ_DETAIL);
		log.info("flowVariables => {}", flowVariables);
	}
	
}
