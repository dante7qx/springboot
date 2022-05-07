package org.dante.springboot.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dante.springboot.enums.FlowEnum;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ExclusiveGateway;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.GraphicInfo;
import org.flowable.bpmn.model.ParallelGateway;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.Task;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cmd.AbstractDynamicInjectionCmd;
import org.flowable.engine.impl.context.Context;
import org.flowable.engine.impl.dynamic.BaseDynamicSubProcessInjectUtil;
import org.flowable.engine.impl.dynamic.DynamicUserTaskBuilder;
import org.flowable.engine.impl.persistence.entity.DeploymentEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

import com.google.common.collect.Maps;

import cn.hutool.core.util.IdUtil;

/**
 * 动态任务加签
 * 
 * @author dante
 *
 */
public class SpiritInjectParallelUserTaskCmd extends AbstractDynamicInjectionCmd implements Command<Void> {
	
	protected String taskId;
	protected String processInstanceId;
    protected DynamicUserTaskBuilder dynamicUserTaskBuilder;
    
    public SpiritInjectParallelUserTaskCmd(String taskId, String processInstanceId, DynamicUserTaskBuilder dynamicUserTaskBuilder) {
    	this.taskId = taskId;
    	this.processInstanceId = processInstanceId;
		this.dynamicUserTaskBuilder = dynamicUserTaskBuilder;
    }

	@Override
	public Void execute(CommandContext commandContext) {
		createDerivedProcessDefinitionForProcessInstance(commandContext, processInstanceId);
		return null;
	}

	@Override
	protected void updateBpmnProcess(CommandContext commandContext, Process process, BpmnModel bpmnModel,
			ProcessDefinitionEntity originalProcessDefinitionEntity, DeploymentEntity newDeploymentEntity) {
		// 启动事件
		List<StartEvent> startEvents = process.findFlowElementsOfType(StartEvent.class);
        StartEvent initialStartEvent = null;
        for (StartEvent startEvent : startEvents) {
            if (startEvent.getEventDefinitions().size() == 0) {
                initialStartEvent = startEvent;
                break;
                
            } else if (initialStartEvent == null) {
                initialStartEvent = startEvent;
            }
        }
        // 找到当前用户任务节点的实体
		TaskService taskService = CommandContextUtil.getTaskService(commandContext);
		TaskEntity taskEntity = taskService.getTask(taskId);
		if(taskEntity == null){
		  throw new FlowableObjectNotFoundException("task:"+taskId+" not found");
		}
		//查找当前节点对应的执行执行实体（感觉兴趣的可以搜下流程执行的实例信息 表为ACT_RU_EXECUTION）
	    ExecutionEntity currentExecutionEntity = CommandContextUtil.getExecutionEntityManager(commandContext).findById(taskEntity.getExecutionId());
	    if(currentExecutionEntity == null){
	      throw new FlowableObjectNotFoundException("task:"+taskId+",execution:"+taskEntity.getExecutionId()+" not found");
	    }
	    //定义的节点id
	    String activityId = currentExecutionEntity.getActivityId();
	    FlowElement flowElement = process.getFlowElement(activityId,true);
	    if(!(flowElement instanceof Task)){
	      throw new FlowableException("task type error");
	    }
	    // 创建排他网关
	    ExclusiveGateway exclusiveGateway  = new ExclusiveGateway();
	    exclusiveGateway.setId("ExclusiveGateway_".concat(IdUtil.fastUUID()));
	    process.addFlowElement(exclusiveGateway);
	    
	    // 用户任务 --> 排他网关
	    SequenceFlow flowFromUserTask = new SequenceFlow(activityId, exclusiveGateway.getId());
        flowFromUserTask.setId("seq_".concat(IdUtil.fastUUID()));
        process.addFlowElement(flowFromUserTask);

	    // 创建并行网关
		ParallelGateway parallelGateway = new ParallelGateway();
		parallelGateway.setId("ParallelGateway_".concat(IdUtil.fastUUID()));
        process.addFlowElement(parallelGateway);
        
        // 排他网关 —-> 并行网关（跳转条件 trigerSubTask == true）
        SequenceFlow flowExclusiveToPrallelGateway = new SequenceFlow(exclusiveGateway.getId(), parallelGateway.getId());
        flowExclusiveToPrallelGateway.setId("seq_".concat(IdUtil.fastUUID()));
        flowExclusiveToPrallelGateway.setConditionExpression("${" + FlowEnum.FLOW_ARG_SUB_TASK.code() + "}");
        process.addFlowElement(flowExclusiveToPrallelGateway);
        
        // 创建结束事件
        EndEvent endEvent0 = new EndEvent();
        endEvent0.setId("endevent_".concat(IdUtil.fastUUID()));
        process.addFlowElement(endEvent0);
        
        // 排他网关 —-> 结束（跳转条件 agree == true）
        SequenceFlow flowExclusiveToEnd = new SequenceFlow(exclusiveGateway.getId(), endEvent0.getId());
        flowExclusiveToEnd.setId("seq_".concat(IdUtil.fastUUID()));
        flowExclusiveToEnd.setConditionExpression("${" + FlowEnum.FLOW_ARG_AGREE.code() + "}");
        process.addFlowElement(flowExclusiveToEnd);
        
        // 创建结束事件
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endevent_".concat(IdUtil.fastUUID()));
        process.addFlowElement(endEvent);
        
        // 创建动态的用户任务
        Map<String, String> dynaUserTaskSeqMap = Maps.newHashMap(); // 动态用户任务，流（并行网关 —> 动态用户任务 | 动态用户任务 —> 并行网关2）
        String[] assigneeArr = dynamicUserTaskBuilder.getAssignee().split(",");
        for (int i = 0; i < assigneeArr.length; i++) {
        	String dynaUserTaskId = dynamicUserTaskBuilder.getId() + (i + 1);
        	UserTask dynaUserTask = new UserTask();
            dynaUserTask.setId(dynaUserTaskId);
            dynaUserTask.setName(dynamicUserTaskBuilder.getName() + (i + 1));
            dynaUserTask.setAssignee(assigneeArr[i]);
            process.addFlowElement(dynaUserTask);
            
            // 并行网关 -> 动态任务
            SequenceFlow flowToDynaUserTask = new SequenceFlow(parallelGateway.getId(), dynaUserTask.getId());
            String flowToDynaUserTaskId = "seq_".concat(IdUtil.fastUUID());
            flowToDynaUserTask.setId(flowToDynaUserTaskId);
            process.addFlowElement(flowToDynaUserTask);
            
            // 动态任务 -> 结束事件
            SequenceFlow flowToEndEvent = new SequenceFlow(dynaUserTask.getId(), endEvent.getId());
            String flowToEndEventId = "seq_".concat(IdUtil.fastUUID());
            flowToEndEvent.setId(flowToEndEventId);
            process.addFlowElement(flowToEndEvent);

            dynaUserTaskSeqMap.put(dynaUserTask.getId(), flowToDynaUserTaskId.concat("|").concat(flowToEndEventId));
		}
        
        /*
        UserTask dynaUserTask = new UserTask();
        dynaUserTask.setId(dynamicUserTaskBuilder.getId());
        dynaUserTask.setName(dynamicUserTaskBuilder.getName());
        dynaUserTask.setAssignee(dynamicUserTaskBuilder.getAssignee());
        process.addFlowElement(dynaUserTask);
        
        SequenceFlow flowToDynaUserTask = new SequenceFlow(parallelGateway.getId(), dynaUserTask.getId());
        flowToDynaUserTask.setId("seq_".concat(IdUtil.fastUUID()));
        process.addFlowElement(flowToDynaUserTask);
        
        // 创建结束Sequence
        SequenceFlow flowToEndEvent = new SequenceFlow(dynaUserTask.getId(), endEvent.getId());
        flowToEndEvent.setId("seq_".concat(IdUtil.fastUUID()));
        process.addFlowElement(flowToEndEvent);
        */
        
        /** 调整流程图布局 */
        // 当前用户任务
        GraphicInfo elementGraphicInfo = bpmnModel.getGraphicInfo(activityId);
        if(elementGraphicInfo != null) {
        	// 排他网关
	        bpmnModel.addGraphicInfo(exclusiveGateway.getId(), new GraphicInfo());
	        bpmnModel.addFlowGraphicInfoList(flowFromUserTask.getId(), createWayPoints());
	        
	        // 结束事件0
	        bpmnModel.addGraphicInfo(endEvent0.getId(), new GraphicInfo());
	        bpmnModel.addFlowGraphicInfoList(flowExclusiveToEnd.getId(), createWayPoints());
        	
            // 并行网关
	        bpmnModel.addGraphicInfo(parallelGateway.getId(), new GraphicInfo());
	        bpmnModel.addFlowGraphicInfoList(flowExclusiveToPrallelGateway.getId(), createWayPoints());
	        
	        // 动态用户任务，流（并行网关 —> 动态用户任务 | 动态用户任务 —> 结束事件）
	        for (Map.Entry<String, String> entry : dynaUserTaskSeqMap.entrySet()) {
				String dynaTaskDefId = entry.getKey();
				String[] seqFlowIdArr = entry.getValue().split("\\|");
				bpmnModel.addGraphicInfo(dynaTaskDefId, new GraphicInfo());
				bpmnModel.addFlowGraphicInfoList(seqFlowIdArr[0], createWayPoints());
				// 结束事件
				if(!bpmnModel.containsItemDefinitionId(endEvent.getId())) {
			        bpmnModel.addGraphicInfo(endEvent.getId(), new GraphicInfo());
				}
		        bpmnModel.addFlowGraphicInfoList(seqFlowIdArr[1], createWayPoints());
			}
	        
	        /*
	        // 动态用户任务
	        bpmnModel.addGraphicInfo(dynaUserTask.getId(), new GraphicInfo());
	        bpmnModel.addFlowGraphicInfoList(flowToDynaUserTask.getId(), createWayPoints());
	        // 结束事件
	        bpmnModel.addGraphicInfo(endEvent.getId(), new GraphicInfo());
	        bpmnModel.addFlowGraphicInfoList(flowToEndEvent.getId(), createWayPoints());
	        */
	        
	        // 调用自动排版方法
	        new BpmnAutoLayout(bpmnModel).execute();
        }

        BaseDynamicSubProcessInjectUtil.processFlowElements(commandContext, process, bpmnModel, originalProcessDefinitionEntity, newDeploymentEntity);
	}

	@Override
	protected void updateExecutions(CommandContext commandContext, ProcessDefinitionEntity processDefinitionEntity,
			ExecutionEntity processInstance, List<ExecutionEntity> childExecutions) {
		ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
//		List<ExecutionEntity> oldExecution = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstance.getProcessInstanceId());
		ExecutionEntity execution = executionEntityManager.createChildExecution(processInstance);
		BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(processDefinitionEntity.getId());

		org.flowable.task.service.TaskService taskService = CommandContextUtil.getTaskService(commandContext);
		List<TaskEntity> taskEntities = taskService.findTasksByProcessInstanceId(processInstanceId);
		// 删除当前活动任务
		for (TaskEntity taskEntity : taskEntities) {
			taskEntity.getIdentityLinks().stream().forEach(identityLinkEntity -> {
				if (identityLinkEntity.isGroup()) {
					taskEntity.deleteGroupIdentityLink(identityLinkEntity.getGroupId(), "candidate");
				} else {
					taskEntity.deleteUserIdentityLink(identityLinkEntity.getUserId(), "participant");
				}
			});
			if (taskEntity.getTaskDefinitionKey().equals(dynamicUserTaskBuilder.getId())) {
				taskService.deleteTask(taskEntity, false);
			}
		}
		// 设置活动后的节点
		String[] assigneeArr = dynamicUserTaskBuilder.getAssignee().split(",");
        for (int i = 0; i < assigneeArr.length; i++) {
        	if(i > 0) {
        		continue;
        	}
        	String dynaUserTaskId = dynamicUserTaskBuilder.getId() + (i + 1);
        	UserTask userTask = (UserTask) bpmnModel.getProcessById(processDefinitionEntity.getKey())
    				.getFlowElement(dynaUserTaskId);
    		execution.setCurrentFlowElement(userTask);
        }
		
		Context.getAgenda().planContinueProcessOperation(execution);
	}

	private List<GraphicInfo> createWayPoints() {
		List<GraphicInfo> wayPoints = new ArrayList<>();
        wayPoints.add(new GraphicInfo(0, 0));
        wayPoints.add(new GraphicInfo(0, 0));
        return wayPoints;
	}
	
}
