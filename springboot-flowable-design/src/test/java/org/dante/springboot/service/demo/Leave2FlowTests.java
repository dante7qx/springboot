package org.dante.springboot.service.demo;

import java.util.List;
import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Leave2FlowTests extends SpringbootFlowableDesignApplicationTests {

	private final String applyUserId = "dante"; // 申请人
	private final String leader = "zhangsan"; // 部门领导
	private final String mainLeader = "lisi"; // 主管领导

	/**
	 * 流程发起
	 */
	@Test
	public void startProcessInstance() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put("days", 7);
		variableMap.put(FlowEnum.FLOW_CANDIDATE.code(), leader);
		this.startProcessInstance("leave-approval-delegate-2", variableMap, true);
	}

	/**
	 * 待办列表
	 */
	@Test
	public void applyTodoList() {
		List<Task> task1s = flowTaskService.todoList(applyUserId);
		log.info("申请人待办列表： => {}", task1s);

		List<Task> task2s = flowTaskService.todoList(leader);
		log.info("领导待办列表： => {}", task2s);

		List<Task> task3s = flowTaskService.todoList(mainLeader);
		log.info("主管待办列表： => {}", task3s);
	}

	/**
	 * 1. 领导审批通过
	 */
	@Test
	public void leaderApproval() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
		variableMap.put(FlowEnum.FLOW_CANDIDATE.code(), mainLeader);
		this.complete(leader, "同意", variableMap);
	}

	/**
	 * 2. 主管审批驳回
	 */
	@Test
	public void mainLeaderReject() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.FALSE);
		this.complete(mainLeader, "不行", variableMap);
	}

	/**
	 * 3. 领导审批驳回
	 */
	@Test
	public void leaderReject() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.FALSE);
		this.complete(leader, "垃圾", variableMap);
	}

	/**
	 * 4. 申请人继续申请
	 * 
	 */
	@Test
	public void applyCommit() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put("days", 2);
		variableMap.put(FlowEnum.FLOW_CANDIDATE.code(), leader);
		this.complete(applyUserId, "申请(2)", variableMap);
	}

	/**
	 * 5. 领导审批通过
	 */
	@Test
	public void leaderApproval2() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
		variableMap.put(FlowEnum.FLOW_CANDIDATE.code(), mainLeader);
		this.complete(leader, "同意(2)", variableMap);
	}

	/**
	 * 6. 主管审批通过
	 */
	@Test
	public void mainLeaderApproval() {
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
		this.complete(mainLeader, "不行", variableMap);
	}

}
