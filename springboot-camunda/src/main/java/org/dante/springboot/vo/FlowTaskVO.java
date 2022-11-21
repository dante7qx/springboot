package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

/**
 * 流程任务参数VO
 * 
 * @author dante
 */
@Data
public class FlowTaskVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String taskId;
	
	private String taskName;
	
	private String taskDefKey;
	
	private String procInsId;
	
	private String bizId;
	
	private String bizUid;
	
	private String bizModel;
	
	private String flowBizDetail;
	
	private String curUserId;
	
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

	@Override
	public String toString() {
		return "FlowTaskVO [taskId=" + taskId + ", taskName=" + taskName + ", taskDefKey=" + taskDefKey + ", procInsId="
				+ procInsId + ", bizId=" + bizId + ", bizUid=" + bizUid + ", bizModel=" + bizModel + ", flowBizDetail="
				+ flowBizDetail + "]";
	}
	
}
