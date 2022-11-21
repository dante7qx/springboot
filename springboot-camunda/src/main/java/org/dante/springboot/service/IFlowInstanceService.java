package org.dante.springboot.service;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.dante.springboot.vo.StartFlowVO;

/**
 * 流程实例服务接口
 * 
 * @author dante
 */
public interface IFlowInstanceService {
	
	/**
	 * 启动流程
	 * 
	 * @param procKey
	 * @param startFlowVO
	 * @return
	 */
	public ProcessInstance startFlowByProcKey(String procKey, StartFlowVO startFlowVO) throws Exception;
	
	/**
	 * 根据流程实例Id删除流程
	 * 
	 * @param procInsId
	 */
	public void deleteFlowByProcInsId(String procInsId);
	
}
