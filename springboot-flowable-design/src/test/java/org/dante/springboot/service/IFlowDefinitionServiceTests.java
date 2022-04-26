package org.dante.springboot.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.vo.FlowDefVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IFlowDefinitionServiceTests extends SpringbootFlowableDesignApplicationTests {
	
	@Autowired
	private IFlowDefinitionService flowDefinitionService;
	
	private static final String processDefinitionId = "leave-approval-delegate-1:1:fc9f0d36-a33c-11ec-835d-12ed173e639b";
	private static final String deploymentId = "bdbe4c71-c50b-11ec-a88b-8e0180fdd0ac";
	
	
	@Test
	public void selectProcessDefinitionList() {
		List<FlowDefVO> defs = flowDefinitionService.selectProcessDefinitionList();
		log.info("FlowDefVO List: {}", defs);
		assertTrue(defs.size() >= 0);
	}
	
	@Test
	public void activateProcessDefinitionById() {
		try {
			flowDefinitionService.activateProcessDefinitionById(processDefinitionId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void suspendProcessDefinitionById() {
		try {
			flowDefinitionService.suspendProcessDefinitionById(processDefinitionId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void deleteDeploymentId() {
		try {
			flowDefinitionService.deleteDeploymentId(deploymentId, false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void selectProcessDefXML() {
		try {
			byte[] byteArr = flowDefinitionService.selectProcessDefXML(processDefinitionId);
			String xml = new String(byteArr, StandardCharsets.UTF_8);
			log.info("{}", xml);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void selectProcessDefImage() {
		try {
			byte[] byteArr = flowDefinitionService.selectProcessDefImage(processDefinitionId);
			String imageBase64 = DatatypeConverter.printBase64Binary(byteArr);
			log.info("{}", imageBase64);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
}
