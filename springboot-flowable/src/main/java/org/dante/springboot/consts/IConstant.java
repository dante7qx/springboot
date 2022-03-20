package org.dante.springboot.consts;

public interface IConstant {

	public static final String FILE_PATH_ROOT = "/Users/dante/Documents/Project/java-world/springboot/springboot-flowable/upload/";

	public static final String LEAVE_PROCESS_DEFINITION_KEY = "leave_approval3";

	public static final String OPER_INSERT = "I"; // 保存不提交
	public static final String OPER_APPLY = "A"; // 提交

	/**
	 * 提交人的变量名称
	 */
	public static final String FLOW_SUBMITTER_VAR = "initiator";
	/**
	 * 提交人节点名称
	 */
	public static final String FLOW_SUBMITTER = "提交人";
	/**
	 * 分配人的变量名称
	 */
	public static final String FLOW_ASSIGNEE_VAR = "assignee";
	/**
	 * 自动跳过节点设置属性
	 */
	public static final String FLOWABLE_SKIP_EXPRESSION_ENABLED = "_FLOWABLE_SKIP_EXPRESSION_ENABLED";
	/**
	 * 挂起状态
	 */
	public static final int SUSPENSION_STATE = 2;
	/**
	 * 激活状态
	 */
	public static final int ACTIVATE_STATE = 1;
}
