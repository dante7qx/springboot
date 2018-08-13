package org.dante.springboot.security.controller;

import java.util.List;

import org.dante.springboot.security.dto.req.RoleReqDto;
import org.dante.springboot.security.dto.resp.BaseResp;
import org.dante.springboot.security.dto.resp.RoleAuthorityTreeDto;
import org.dante.springboot.security.dto.resp.RoleRespDto;
import org.dante.springboot.security.dto.resp.RoleTreeDto;
import org.dante.springboot.security.dto.resp.UserRespDto;
import org.dante.springboot.security.pub.PageReq;
import org.dante.springboot.security.pub.PageResult;
import org.dante.springboot.security.service.RoleService;
import org.dante.springboot.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAuthority('sysmgr.role.query')")
	@PostMapping(value = "/query_role_list")
	public PageResult<RoleRespDto> queryRolePage(PageReq pageReq) {
		PageResult<RoleRespDto> result = null;
		try {
			result = roleService.findPage(pageReq);
		} catch (Exception e) {
			logger.error("queryRolePage error.", e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.role.query')")
	@PostMapping(value = "/query_role_tree")
	public List<RoleTreeDto> queryRoleTree() {
		RoleTreeDto root = new RoleTreeDto();
		root.setId(-1L);
		root.setText("所有角色");
		try {
			List<RoleTreeDto> children = roleService.findRoleTree();
			root.setChildren(children);
		} catch (Exception e) {
			logger.error("queryRoleTree error.", e);
		}
		return Lists.newArrayList(root);
	}
	
	@PreAuthorize("hasAuthority('sysmgr.role.query')")
	@PostMapping(value = "/query_by_id")
	public BaseResp<RoleRespDto> queryByRoleId(Long id) {
		BaseResp<RoleRespDto> result = new BaseResp<RoleRespDto>();
		try {
			RoleRespDto roleResp = roleService.queryById(id);
			result.setData(roleResp);
		} catch (Exception e) {
			result.setFlag(false);
			logger.error("queryByRoleId roleId: {} error.", id, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.role.query')")
	@PostMapping(value = "/query_authority_tree")
	public List<RoleAuthorityTreeDto> queryAuthprityTreeByRoleId(Long roleId) {
		List<RoleAuthorityTreeDto> trees = null;
		try {
			trees = roleService.findRoleAuthorityTreeByRoleId(roleId);
		} catch (Exception e) {
			logger.error("queryAuthprityTreeByRoleId roleId: {} error.", roleId, e);
		}
		return trees;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.role.update')")
	@PostMapping(value = "/update", produces = "application/json")
	public BaseResp<RoleRespDto> updateRole(RoleReqDto roleReqDto) {
		BaseResp<RoleRespDto> result = new BaseResp<RoleRespDto>();
		try {
			RoleRespDto roleResp = roleService.updateRole(roleReqDto);
			result.setData(roleResp);
		} catch (Exception e) {
			result.setFlag(false);
			logger.error("updateRole role: {} error.", roleReqDto, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.role.delete')")
	@DeleteMapping(value = "/delete_by_id")
	public BaseResp<?> deleteByRoleId(Long id) {
		BaseResp<?> result = new BaseResp<>();
		try {
			roleService.deleteById(id);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("deleteByRoleId roleId: {} error.", id, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.role.delete')")
	@DeleteMapping(value = "/delete_by_ids")
	public BaseResp<?> deleteByRoleIds(@RequestParam(value="ids[]") Long[] ids) {
		BaseResp<?> result = new BaseResp<>();
		try {
			roleService.deleteByIds(ids);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("deleteByRoleId roleIds: {} error.", ids, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.role.query')")
	@PostMapping(value = "/query_role_user")
	public List<UserRespDto> queryUserByRoleId(Long roleId) {
		List<UserRespDto> userResps = null;
		try {
			userResps = userService.findByRoleId(roleId);
		} catch (Exception e) {
			logger.error("queryUserByRoleId roleId: {} error.", roleId, e);
		}
		return userResps;
	}
	
}
