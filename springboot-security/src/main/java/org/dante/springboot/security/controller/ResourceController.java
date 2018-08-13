package org.dante.springboot.security.controller;

import java.util.List;

import org.dante.springboot.security.dto.req.ResourceReqDto;
import org.dante.springboot.security.dto.resp.BaseResp;
import org.dante.springboot.security.dto.resp.ResourceRespDto;
import org.dante.springboot.security.dto.resp.ResourceTreeDto;
import org.dante.springboot.security.pub.EasyUIDragTreeReq;
import org.dante.springboot.security.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RequestMapping("/resource")
@RestController
public class ResourceController {
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resourceService;
	
	@PreAuthorize("hasAuthority('sysmgr.resource.query')")
	@PostMapping("/query_tree")
	public List<ResourceTreeDto> queryResourceTree() {
		List<ResourceTreeDto> trees = Lists.newArrayList();
		try {
			ResourceTreeDto root = new ResourceTreeDto();
			root.setId(-1L);
			root.setText("所有菜单");
			root.setState("open");
			root.getChildren().addAll(resourceService.findResourceTree());
			trees.add(root);
		} catch (Exception e) {
			logger.error("queryResourceTree error.", e);
		}
		return trees;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.resource.update')")
	@PostMapping("/update")
	public BaseResp<ResourceRespDto> updateResource(ResourceReqDto resourceReq) {
		BaseResp<ResourceRespDto> result = new BaseResp<ResourceRespDto>();
		try {
			ResourceRespDto authorityResp = resourceService.updateResource(resourceReq);
			result.setData(authorityResp);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("updateResource resourceReq: {} error.", resourceReq, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.resource.update')")
	@PostMapping("/update_when_drag")
	public BaseResp<?> updateAuthorityWhenDrap(EasyUIDragTreeReq dragTreeReq) {
		BaseResp<?> result = new BaseResp<>();
		try {
			resourceService.updateAuthorityWhenDrag(dragTreeReq);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("updateAuthorityWhenDrap dragTree {} error.", dragTreeReq, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.resource.delete')")
	@DeleteMapping("/delete_by_id")
	public BaseResp<?> deleteById(Long id) {
		BaseResp<?> result = new BaseResp<>();
		try {
			resourceService.deleteById(id);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("deleteById id: {} error.", id, e);
		}
		return result;
	}
	
}
