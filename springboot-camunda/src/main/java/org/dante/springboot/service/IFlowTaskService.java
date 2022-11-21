package org.dante.springboot.service;

import java.util.List;
import java.util.Map;

import org.dante.springboot.vo.FlowTaskVO;

/**
 * 流程任务服务接口
 * 
 * @author dante
 */
public interface IFlowTaskService {
	
	/**
	 * 待办任务
	 * 
	 * @param vo
	 */
	public List<FlowTaskVO> todoList(FlowTaskVO vo);
	
	/** 
	 * 获取流程变量
	 *  
	 * @param procInsId
	 * @param key
	 * @return
	 */
	public String getFlowVariable(String procInsId, String key) throws Exception;
	
	/**
	 * 获取流程变量
	 * 
	 * @param procInsId
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFlowVariables(String procInsId, String... keys) throws Exception;
	
	/**
	 * 审批流程
	 * 
	 * @param flowTaskVO
	 * @throws Exception
	 */
	public void approval(FlowTaskVO flowTaskVO) throws Exception;

}
