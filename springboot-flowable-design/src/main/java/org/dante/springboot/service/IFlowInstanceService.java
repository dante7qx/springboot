package org.dante.springboot.service;

import org.dante.springboot.vo.StartFlowInstanceVO;
import org.flowable.engine.runtime.ProcessInstance;

/**
 * 流程实例服务接口
 * 
 * @author dante
 *
 */
public interface IFlowInstanceService {
	
	/**
	 * 根据流程定义Key启动流程
	 * 
	 * @param vo 流程启动类
	 * @throws Exception
	 */
	public ProcessInstance startProcessInstance(StartFlowInstanceVO vo) throws Exception;
	
	/**
	 * 根据流程定义Key启动流程
	 * 
	 * @param vo 流程启动类
	 * @param autoCompleteTask 自动完成第一个任务
	 * @throws Exception
	 */
	public ProcessInstance startProcessInstance(StartFlowInstanceVO vo, boolean autoCompleteTask) throws Exception;
	
	/**
	 * 删除流程实例
	 * 
	 * @param processInstanceId
	 * @throws Exception
	 */
	public void deleteProcessInstance(String processInstanceId) throws Exception;;
	
	
}
