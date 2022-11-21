package org.dante.springboot.consts;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流程定义Key常量
 * 
 * @author dante
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlowDefKeyContanst {

	/** 请假流程 */
	public static final String FLOW_LEAVE_KEY = "FLOW_LEAVE_KEY";

}
