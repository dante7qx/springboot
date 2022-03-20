package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * 流程任务VO
 * 
 * @author dante
 *
 */
@Data
public class FlowTaskVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String processInstanceId;
	
	private String currentUserId;
	
	private String taskId;
	
	private String taskName;
	
	private String businessKey;
	
	private String commentType;
	
	private String commentValue;
	
	private Map<String, Object> params;
	
	public Map<String, Object> addParams(String key, Object val) {
		if(CollectionUtils.isEmpty(params)) {
			this.params = Maps.newHashMap();
		}
		this.params.put(key, val);
		return this.params;
	}

}
