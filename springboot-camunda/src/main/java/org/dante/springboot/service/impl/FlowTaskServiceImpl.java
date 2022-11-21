package org.dante.springboot.service.impl;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.dante.springboot.consts.FlowContanst;
import org.dante.springboot.service.FlowServiceFactory;
import org.dante.springboot.service.IFlowTaskService;
import org.dante.springboot.vo.FlowTaskVO;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.hutool.core.collection.CollUtil;

@Service
public class FlowTaskServiceImpl extends FlowServiceFactory implements IFlowTaskService {

	@Override
	public List<FlowTaskVO> todoList(FlowTaskVO vo) {
		List<FlowTaskVO> vos = Lists.newArrayList();
		List<Group> groups = identityService.createGroupQuery().groupMember(vo.getCurUserId()).list();
		List<Task> tasks = taskService.createTaskQuery()
				.active()
				.or()
					.taskAssigneeIn(vo.getCurUserId())
					.taskCandidateUser(vo.getCurUserId())
					.taskCandidateGroupIn(groups.stream().map(Group::getId).toList())
				.endOr()
				.orderByTaskCreateTime().asc().list();
		if(CollUtil.isNotEmpty(tasks)) {
			for (Task task : tasks) {
				vos.add(convert2FlowTaskVO(task));
			}
		}
		return vos;
	}
	
	@Override
	public String getFlowVariable(String procInsId, String key) throws Exception {
		HistoricVariableInstance instance = historyService
				.createHistoricVariableInstanceQuery()
				.processInstanceId(procInsId)
				.variableName(key)
				.singleResult();
		if(instance == null) {
			throw new Exception("Variable " + key + " not exist");
		}
		return instance.getValue().toString();
	}
	
	/**
	 * 获取流程变量
	 * 
	 * @param procInsId
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getFlowVariables(String procInsId, String... keys) throws Exception {
		Map<String, Object> result = Maps.newHashMap();
		List<VariableInstance> variables = runtimeService.createVariableInstanceQuery().processInstanceIdIn(procInsId)
				.variableNameIn(keys).list();
		if(CollUtil.isNotEmpty(variables)) {
			for (VariableInstance variable : variables) {
				result.put(variable.getName(), variable.getValue());
			}
		}
		return result;
	}

	/**
	 * 审批流程
	 * 
	 * @param flowTaskVO
	 * @throws Exception
	 */
	@Override
	public void approval(FlowTaskVO vo) throws Exception {
		Assert.hasText(vo.getComment(), "审批意见不能为空！");
		Assert.hasText(vo.getCurUserId(), "审批人不能为空！");
		taskService.createComment(vo.getTaskId(), vo.getProcInsId(), vo.getComment());
		taskService.setAssignee(vo.getTaskId(), vo.getCurUserId());
		taskService.complete(vo.getTaskId(), vo.getParams());
	}

	private FlowTaskVO convert2FlowTaskVO(Task task) {
		FlowTaskVO vo = new FlowTaskVO();
		vo.setTaskId(task.getId());
		vo.setTaskName(task.getName());
		vo.setTaskDefKey(task.getTaskDefinitionKey());
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().active().processInstanceId(task.getProcessInstanceId()).singleResult();
		vo.setProcInsId(processInstance.getId());
		vo.setBizId(processInstance.getBusinessKey());
		try {
			Map<String, Object> vars = this.getFlowVariables(task.getProcessInstanceId(), FlowContanst.VAL_BIZ_UID, FlowContanst.VAL_BIZ_MODEL, FlowContanst.VAL_FLOW_BIZ_DETAIL);
			vo.setBizUid(vars.get(FlowContanst.VAL_BIZ_UID).toString());
			vo.setBizModel(vars.get(FlowContanst.VAL_BIZ_MODEL).toString());
			vo.setFlowBizDetail(vars.get(FlowContanst.VAL_FLOW_BIZ_DETAIL).toString());
		} catch (Exception e) {
		}
		return vo;
	}

}
