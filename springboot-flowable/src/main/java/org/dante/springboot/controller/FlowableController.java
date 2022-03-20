package org.dante.springboot.controller;

import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.dante.springboot.dto.DeployProcessDTO;
import org.dante.springboot.po.DeployProcessPO;
import org.dante.springboot.service.IFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/flow")
public class FlowableController {
	
	@Autowired
	private IFlowService flowService;
	
	/**
	 * 获取流程定义列表
	 * 
	 * @return
	 */
	@GetMapping("/query_flow_define")
	@ResponseBody
	public List<DeployProcessPO> queryDeployProcessList() {
		return flowService.findDeployProcess();
	}
	
	/**
	 * 部署流程定义
	 * 
	 * @param attachments
	 * @param deployProcessDTO
	 * @return
	 */
	@PostMapping("/create_flow_define")
	@ResponseBody
	public DeployProcessPO createDeployProcess(@RequestParam("attachments") MultipartFile[] attachments, DeployProcessDTO deployProcessDTO) {
		DeployProcessPO po = null;
		try {
			po = flowService.saveDeployProcess(attachments, deployProcessDTO);
		} catch (Exception e) {
			log.error("新增流程定义错误!", e);
		}
		return po;
	}
	
	@PostMapping("/find_flow_chart/{deployProcessId}")
	public String findProcessDefineImage(@PathVariable Long deployProcessId) {
		log.info("{} 获取流程定义流程图", deployProcessId);
		String chart = "";
		try {
			byte[] imageByte = flowService.findFlowChartByDeployProcessId(deployProcessId);
			chart = DatatypeConverter.printBase64Binary(imageByte);
		} catch (Exception e) {
			log.error("获取流程定义流程图错误!", e);
		}
		return chart;
	}
	
	@GetMapping("/find_flow_inst_chart/{procInstId}")
	public String findProcessInstanceChart(@PathVariable String procInstId) {
		String chart = "";
		try {
			byte[] imageByte = flowService.findRuntimeFlowChart(procInstId);
			chart = DatatypeConverter.printBase64Binary(imageByte);
		} catch (Exception e) {
			log.error("获取流程实例流程图错误!", e);
		}
		return chart;
	}

}
