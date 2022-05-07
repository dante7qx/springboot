package org.dante.springboot.enums;

public enum FlowEnum {

	/** 流程定义KEY **/
	FLOW_DEF_KEY_LEAVE_DELEGATE("leave-approval-delegate-1", "请假审批(委派)"),
	FLOW_DEF_KEY_RECE_VE_DOC_SUB_FLOW("RECIVE_DOC_SUB_FLOW", "收文流程（嵌套子流程）"),
	
	/** 流程发起人 **/
	FLOW_INITIATOR("initiator", "流程发起人常量"),
	FLOW_CANDIDATE("approval", "候选人（组）变量"),
	FLOW_CANDIDATE_ROLE_LIST("roleList", "多实例候选组"),
	FLOW_CANDIDATE_USER_LIST("userList", "多实例候选人"),
	
	/** 流程条件常量 **/
	FLOW_ARG_AUTO_SKIP("_FLOWABLE_SKIP_EXPRESSION_ENABLED", ""),
	FLOW_COMMAND("command", "流程任务完成条件Key"),
	FLOW_FINISHED("FINISHED", "流程结束标识"),
	
	FLOW_ARG_AGREE("agree", "审批是否同意标志"),
	FLOW_ARG_SUB_TASK("trigerSubTask", "审批是否同意标志"),
	
	FLOW_TURN_TODO("TURN_TODO", "转办"),
	FLOW_DELEGATE("DELEGATE", "委派"),
	
	/** 用户操作：草稿、提交 **/
	FLOW_OPER_INSERT("I", "草稿"),
	FLOW_OPER_APPL("A", "提交");
	
	
	
	private String code;
	private String value;
	private String desc;
	
	private FlowEnum(String code, String desc) {
		this.code = code;
		this.value = null;
		this.desc = desc;
	}
	
	private FlowEnum(String code, String value, String desc) {
		this.code = code;
		this.value = value;
		this.desc = desc;
	}
	
	public String code() {
		return this.code;
	}
	
	public String value() {
		return this.value;
	}
	
	public String desc() {
		return this.desc;
	}
	
}
