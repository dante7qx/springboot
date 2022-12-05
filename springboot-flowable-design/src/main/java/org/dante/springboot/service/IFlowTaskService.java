package org.dante.springboot.service;

import java.util.List;
import java.util.Map;

import org.dante.springboot.vo.FlowTaskVO;
import org.flowable.task.api.Task;

/**
 * 流程任务服务接口 
 * 
 * @author dante
 *
 */
public interface IFlowTaskService {
	
	/**
	 * 待办任务
	 * 
	 * @param userId（或GroupId）
	 * @return
	 */
	public List<Task> todoList(String userId);
	
	/**
	 * 完成任务
	 * 
	 * @param taskId
	 * @param flowTaskVO
	 */
	public void complete(String taskId, FlowTaskVO flowTaskVO);
	
	/**
	 * 重置处理人
	 *     保留我的待办   ==> 委派
	 *     不保留我的待办 ==> 转让
	 * 
	 * @param taskId
	 * @param currentUserId
	 * @param acceptUserId
	 * @param keepMyTodo 保留我的待办
	 */
	public void assignOtherUser(String taskId, String currentUserId, String acceptUserId, boolean keepMyTodo, String reason);
	
	/**
	 * 委派任务 
	 * 是将任务节点分给其他人处理，等其他人处理好之后，委派任务会自动回到委派人的任务中
	 * 
	 * @param taskId
	 * @param currentUserId 委派用户，即当前用户
	 * @param acceptUserId 被委派用户
	 * @param reason       委派原因
	 */
	public void delegate(String taskId, String currentUserId, String acceptUserId, String reason);
	
	/**
	 * 转办任务
	 * 
	 * @param taskId
	 * @param currentUserId
	 * @param acceptUserId
	 * String reason
	 */
	public void turnTodo(String taskId, String currentUserId, String acceptUserId, String reason);
	
	/**
     * 获取流程变量
     * 
     * @param taskId
     * @return
     */
	public Map<String, Object> processVariablesByTaskId(String taskId);
    
    /**
     * 获取流程变量
     * 
     * @param procInsId
     * @return
     */
	public Map<String, Object> processVariablesByProcInsId(String procInsId);
	
	public static void main(String[] args) {
		String xx = "部门负责人11";
		System.out.println(xx.replaceAll("部门负责人", ""));
	}
}
