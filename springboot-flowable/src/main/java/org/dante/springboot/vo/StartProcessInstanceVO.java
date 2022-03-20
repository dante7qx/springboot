package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class StartProcessInstanceVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 流程定义key 必填
     */
    private String processDefinitionKey;
    /**
     * 业务系统id 必填
     */
    private String businessKey;
    /**
     * 启动流程变量 选填
     */
    private Map<String, Object> variables;
    /**
     * 申请人工号 必填
     */
    private String applicant;
    /**
     * 系统标识 必填
     */
    private String systemSn;
    /**
     * 表单显示名称 必填
     */
    private String formName;
    /**
     * 流程提交人工号(操作人员) 必填
     */
    private String submitter;
    /**
     * 操作类型 (判断当前任务完成、或者发起)
     */
    private String operType;
}
