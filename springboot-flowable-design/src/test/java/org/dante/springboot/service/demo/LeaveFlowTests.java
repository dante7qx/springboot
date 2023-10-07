package org.dante.springboot.service.demo;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.vo.VariableVO;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class LeaveFlowTests extends SpringbootFlowableDesignApplicationTests {
	
	private final VariableVO variable = new VariableVO(100L, "但丁", Date.from(Instant.now()), Lists.newArrayList("宣称部", "组织部"));
	
	private final String applyUserId = "dante";			// 申请人	
	private final String approvalUserId = "lizhuren";	// 审批人
	private final String delegateUserId = "haizhuren"; // 委派人
	
	
	@Test
	void startProcessInstance() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put("entity", variable);
		this.startProcessInstance("leave-approval-delegate-1", variableMap);
	}
	
	/**
	 * 待办列表
	 */
	@Test
	void todoList() {
		String currentUserId = approvalUserId; 
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		log.info("Tasks => {}", tasks);
		if(!CollectionUtils.isEmpty(tasks)) {
			VariableVO variable = (VariableVO) runtimeService.getVariable(tasks.get(0).getExecutionId(), "entity");
			log.info("{}", variable);
		}
		
	}
	
	/**
	 * 提交请假申请
	 */
	@Test
	void commitLeave() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put("leaveDays", 7);
		variableMap.put("approval", approvalUserId);
		this.complete(applyUserId, "休年假，处理私事", variableMap);
	}
	
	/**
	 * 李主任委派给韩主任审批
	 * 
	 */
	@Test
	void assignOtherUser() {
		List<Task> tasks = flowTaskService.todoList(approvalUserId);
		Task task = tasks.get(0);
		if(task == null) {
			log.info("{} 没有待办任务", approvalUserId);
		}
		// 委派
		flowTaskService.assignOtherUser(task.getId(), approvalUserId, delegateUserId, true, "我有事，请帮我处理！");
	}
	
	/**
	 *	领导审批 
	 */
	@Test
	void approval() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put("command", Boolean.TRUE);
		this.complete(approvalUserId, "同意", variableMap);
	}

}
