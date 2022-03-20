package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * 启动流程实例 VO
 * 
 * @author dante
 *
 */
@Data
public class StartFlowInstanceVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 流程定义 Key **/
	private String processDefKey;
	
	/** 流程发起人 Id **/
	private String starterId;
	
	/** 业务表的标识，可以使用Id 或者组合（bizTableName_bizId）**/
	private String bussinessKey;
	
	/** 用户操作，保存草稿、直接提交 **/
	private String operType;
	
	/** 流程变量，根据流程定义进行设置 **/
	private Map<String, Object> params;
	
	/** 审批意见类型 **/
	private String commentType;
	
	/** 审批意见 **/
	private String commentValue;
	
	public Map<String, Object> addParams(String key, Object val) {
		if(CollectionUtils.isEmpty(params)) {
			this.params = Maps.newHashMap();
		}
		this.params.put(key, val);
		return this.params;
	}
	
	
	

}
