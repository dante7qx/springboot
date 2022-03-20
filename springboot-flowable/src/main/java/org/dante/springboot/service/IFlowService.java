package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.dto.DeployProcessDTO;
import org.dante.springboot.po.DeployProcessPO;
import org.dante.springboot.vo.CommentVo;
import org.dante.springboot.vo.StartProcessInstanceVO;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.web.multipart.MultipartFile;

public interface IFlowService {
	
	/**
	 * 查询已部署的流程定义
	 * 
	 * @return
	 */
	public List<DeployProcessPO> findDeployProcess();
	
	
	/**
	 * 新增流程定义
	 * 
	 * @param deployProcessDTO
	 * @return
	 */
	public DeployProcessPO saveDeployProcess(MultipartFile[] attachments, DeployProcessDTO deployProcessDTO) throws Exception;
	
	/**
	 * 根据Id查询流程定义
	 * 
	 * @param id
	 * @return
	 */
	public DeployProcessPO findDeployProcessById(Long id);
	
	/**
	 * 根据流程部署Id获取流程图
	 * 
	 * @param deploymentId
	 * @param response
	 */
	public byte[] findFlowChartByDeployProcessId(Long deployProcessId) throws Exception;
	
	/**
	 * 获取流程图，显示路径和节点
	 * 
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	public byte[] findRuntimeFlowChart(String processInstanceId) throws Exception;
	
	/**
	 * 启动流程
	 * 
	 * @param params
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(StartProcessInstanceVO params) throws Exception;
	
	/**
     * 添加备注
     * @param comment 参数
     */
    public void addComment(CommentVo comment) ;

    /**
     * 通过流程实例id获取审批意见列表
     * @param processInstanceId 流程实例id
     * @return
     */
    public List<CommentVo> getFlowCommentVosByProcessInstanceId(String processInstanceId) ;

}
