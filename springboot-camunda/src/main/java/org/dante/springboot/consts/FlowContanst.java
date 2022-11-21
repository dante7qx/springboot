package org.dante.springboot.consts;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流程常量
 * 
 * @author dante
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlowContanst {
	
	/** 候选人(组) */
	public static final String VAL_APPROVAL = "approval";
	
	/** 多实例集合 */
	public static final String VAL_USER_LIST = "userList";
	
	/** 业务标识 */
	public static final String VAL_BIZ_UID = "bizUid";
	
	/** 业务模块 */
	public static final String VAL_BIZ_MODEL = "bizModel";
	
	/** 流程详情描述 */
	public static final String VAL_FLOW_BIZ_DETAIL = "flowBizDetail";
	
	/** 审批通过参数 */
	public static final String VAL_FLOW_AGREE = "agree";
	
}
