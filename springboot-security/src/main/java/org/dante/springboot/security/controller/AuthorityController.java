package org.dante.springboot.security.controller;

import java.util.List;

import org.dante.springboot.security.dto.req.AuthorityReqDto;
import org.dante.springboot.security.dto.resp.AuthorityRespDto;
import org.dante.springboot.security.dto.resp.AuthorityTreeDto;
import org.dante.springboot.security.dto.resp.BaseResp;
import org.dante.springboot.security.pub.EasyUIDragTreeReq;
import org.dante.springboot.security.service.AuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RequestMapping("/authority")
@RestController
public class AuthorityController {
	private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);
	
	@Autowired
	private AuthorityService authorityService;
	
	@PreAuthorize("hasAuthority('sysmgr.authority.query')")
	@PostMapping("/query_tree")
	public List<AuthorityTreeDto> queryAuthorityTree() {
		List<AuthorityTreeDto> trees = Lists.newArrayList();
		try {
			AuthorityTreeDto root = new AuthorityTreeDto();
			root.setId(-1L);
			root.setText("所有权限");
			root.setState("open");
			List<AuthorityTreeDto> childTrees = authorityService.findAuthorityTree();
			root.setChildren(childTrees);
			trees.add(root);
		} catch (Exception e) {
			logger.error("queryRootAuthorityTree error.", e);
		}
		return trees;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.authority.query')")
	@PostMapping("/query_combotree")
	public List<AuthorityTreeDto> queryAuthorityComboTree() {
		List<AuthorityTreeDto> trees = null;
		try {
			trees = authorityService.findAuthorityTree();
		} catch (Exception e) {
			logger.error("queryAuthorityTree error.", e);
		}
		return trees;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.authority.update')")
	@PostMapping("/update")
	public BaseResp<AuthorityRespDto> updateAuthority(AuthorityReqDto authorityReq) {
		BaseResp<AuthorityRespDto> result = new BaseResp<AuthorityRespDto>();
		try {
			AuthorityRespDto authorityResp = authorityService.updateAuthority(authorityReq);
			result.setData(authorityResp);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("updateAuthority authorityReq: {} error.", authorityReq, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.authority.update')")
	@PostMapping("/update_when_drag")
	public BaseResp<?> updateAuthorityWhenDrap(EasyUIDragTreeReq dragTreeReq) {
		BaseResp<?> result = new BaseResp<>();
		try {
			authorityService.updateAuthorityWhenDrag(dragTreeReq);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("updateAuthorityWhenDrap dragTree {} error.", dragTreeReq, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.authority.delete')")
	@DeleteMapping("/delete_by_id")
	public BaseResp<?> deleteById(Long id) {
		BaseResp<?> result = new BaseResp<>();
		try {
			authorityService.deleteById(id);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("deleteById id: {} error.", id, e);
		}
		return result;
	}
	
}
