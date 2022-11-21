package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

/**
 * 流程启动VO
 * 
 * @author dante
 */
@Data
public class StartFlowVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 业务模块参数 */
	private String bizId;
	private String bizUid;
	private String bizModel;
	
	/** 流程发起人 */
	private String startUserId = "dante";
	/** 流程详情描述 */
	private String flowBizDetail;
	
	/** 流程启动意见 */
	private String comment;
	
	/** 流程参数 */
	private Map<String, Object> params;
	
	public void addParams(String key, Object value) {
		if(CollUtil.isEmpty(params)) {
			this.params = Maps.newHashMap();
		}
		this.params.put(key, value);
	}
}
