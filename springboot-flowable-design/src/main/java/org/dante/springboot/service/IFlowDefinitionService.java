package org.dante.springboot.service;

import java.io.File;
import java.util.List;

import org.dante.springboot.vo.FlowDefVO;

/**
 * 流程定义服务接口
 * 
 * @author dante
 *
 */
public interface IFlowDefinitionService {
	
	/**
	 * 部署流程
	 * 
	 * @param bpmn20XML
	 * @return 流程部署Id
	 */
	public String createProcessDefinition(File bpmn20XML);
	
	/**
	 * 获取流程定义列表
	 * 
	 * @return
	 */
	public List<FlowDefVO> selectProcessDefinitionList();
	
	/**
	 * 激活指定Id的流程定义
	 * 
	 * @param processDefId
	 * @throws Exception
	 */
	public void activateProcessDefinitionById(String processDefId) throws Exception;
	
	/**
	 * 挂起指定Id的流程定义
	 * 
	 * @param processDefId
	 * @throws Exception
	 */
	public void suspendProcessDefinitionById(String processDefId) throws Exception;
	
	/**
	 * 删除已部署的流程定义
	 * 
	 * @param deploymentId
	 * @param cascade  级联删除关联的流程实例、历史流程实例、jobs数据
	 * @throws Exception
	 */
	public void deleteDeploymentId(String deploymentId, boolean cascade) throws Exception;
	
	
	/**
	 * 获取流程定义XML
	 * 
	 * @param processDefId
	 * @return
	 */
	public byte[] selectProcessDefXML(String processDefId) throws Exception;
	
	/**
	 * 获取流程定义图
	 * 
	 * @param processDefId
	 * @return
	 */
	public byte[] selectProcessDefImage(String processDefId) throws Exception;
	
}
