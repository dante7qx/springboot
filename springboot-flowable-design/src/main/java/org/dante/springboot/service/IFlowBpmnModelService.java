package org.dante.springboot.service;

import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowNode;

/**
 * Flowable 流程BpmnModel服务类
 * 
 * @author dante
 *
 */
public interface IFlowBpmnModelService {
	/**
     * 通过流程定义id获取BpmnModel
     *
     * @param processDefId 流程定义id
     * @return
     */
    public BpmnModel getBpmnModelByProcessDefId(String processDefId);
    
    /**
     * 通过流程定义id获取所有的节点
     *
     * @param processDefId 流程定义id
     * @return
     */
    public List<FlowNode> findFlowNodes(String processDefId);
    
    /**
     * 获取end节点
     *
     * @param processDefId 流程定义id
     * @return FlowElement
     */
    public List<EndEvent> findEndFlowElement(String processDefId);
    
    
}
