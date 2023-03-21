package org.dante.springboot.service.demo;

import java.util.List;
import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DevFlowTests extends SpringbootFlowableDesignApplicationTests {

	String startUserId = "dante";
	String procInstId = "238168da-c310-11ed-b48b-161abcb90495";
						 
	@Test
	public void startProcessInstance() {
		this.startProcessInstance("fk_123");
		curTaskByProcInstId();
	}
	
	@Test
	public void multiApproval() {
		List<String> userList = Lists.newArrayList("zhangsan", "lisi", "wangwu");
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put("userList", userList);
		this.complete(startUserId, "申请", variableMap);
		curTaskByProcInstId();
	}
	
	@Test
	public void approval1() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
		this.complete("zhangsan", "张三通过", variableMap);
		curTaskByProcInstId();
	}
	
	@Test
	@Transactional
	public void approval2() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.FALSE);
//		this.complete("lisi", "李四退回");
//		curTaskByProcInstId();
		
		String[] userArr = new String[] {"lisi", "wangwu"}; 
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstId).active().list();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			taskService.setAssignee(task.getId(), userArr[i]);
			taskService.complete(task.getId(), variableMap);
		}
				
	}
	
	@Test
	public void approval3() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
		this.complete("wangwu", "王五通过", variableMap);
		curTaskByProcInstId();
	}
	
	@Test
	public void leaderApproval() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.FALSE);
		this.complete("haizhuren", "韩主任退回", variableMap);
		curTaskByProcInstId();
	}
	
	
	private void curTaskByProcInstId() {
		log.info("==========================================================================================================");
		List<Task> curTasks = taskService.createTaskQuery().processInstanceId(procInstId).active().list();
		// 多实例
		for (Task task : curTasks) {
			log.info("{} - {} - {} - {}", task.getId(), task.getName(), task.getTaskDefinitionKey(), task.getDescription());
//			Object agree = taskService.getVariable(task.getId(), FlowEnum.FLOW_ARG_AGREE.code());
			log.info("审批结果 -> 从审批历史中获取");
		}
		log.info("==========================================================================================================");
	}
	
}
