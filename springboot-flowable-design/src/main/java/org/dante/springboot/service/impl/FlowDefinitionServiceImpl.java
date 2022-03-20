package org.dante.springboot.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.dante.springboot.service.FlowServiceFactory;
import org.dante.springboot.service.IFlowDefinitionService;
import org.dante.springboot.vo.FlowDefVO;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.google.common.collect.Lists;

@Service
public class FlowDefinitionServiceImpl extends FlowServiceFactory implements IFlowDefinitionService {

	@Override
	public String createProcessDefinition(File bpmn20xml) {
		return null;
	}

	@Override
	public List<FlowDefVO> selectProcessDefinitionList() {
		List<FlowDefVO> flowDefs = Lists.newArrayList();
		List<ProcessDefinition> processDefs = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().desc().list();
		processDefs.forEach(d -> {
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(d.getDeploymentId()).orderByDeploymentTime().desc().singleResult();
			FlowDefVO flowDef = new FlowDefVO();
			flowDef.setProcessDefId(d.getId());
			flowDef.setProcessDefName(d.getName());
			flowDef.setProcessDefVersion(d.getVersion());
			flowDef.setProcessDeployId(d.getDeploymentId());
			flowDef.setProcessDeployTime(deployment.getDeploymentTime());
			flowDef.setXml(d.getResourceName());
			flowDef.setDiagram(d.getDiagramResourceName());
			flowDef.setSuspended(d.isSuspended());
			flowDefs.add(flowDef);
		});
		
		return flowDefs;
	}
	
	@Override
	public void activateProcessDefinitionById(String processDefId) throws Exception {
		try {
			repositoryService.activateProcessDefinitionById(processDefId);
		} catch (FlowableException e) {
			throw new Exception("流程定义不存在，或该流程已是激活状态", e);
		}
		
	}

	@Override
	public void suspendProcessDefinitionById(String processDefId) throws Exception {
		try {
			repositoryService.suspendProcessDefinitionById(processDefId);
		} catch (FlowableException e) {
			throw new Exception("流程定义不存在，或该流程已被挂起", e);
		}
	}
	
	@Override
	public void deleteDeploymentId(String deploymentId, boolean cascade) throws Exception {
		try {
			repositoryService.deleteDeployment(deploymentId, cascade);
		} catch (Exception e) {
			throw new Exception("流程定义删除失败", e);
		}
		
	}

	@Override
	public byte[] selectProcessDefXML(String processDefId) throws Exception {
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefId);
		if(processDefinition == null) {
			throw new Exception("流程定义不存在");
		}
		InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
		return FileCopyUtils.copyToByteArray(inputStream);
	}

	@Override
	public byte[] selectProcessDefImage(String processDefId) throws Exception {
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefId);
		if(processDefinition == null) {
			throw new Exception("流程定义不存在");
		}
		InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
		return FileCopyUtils.copyToByteArray(inputStream);
	}

}
