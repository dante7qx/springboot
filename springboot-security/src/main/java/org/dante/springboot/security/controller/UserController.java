package org.dante.springboot.security.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.dante.springboot.security.domain.User;
import org.dante.springboot.security.dto.req.UserReqDto;
import org.dante.springboot.security.dto.resp.BaseResp;
import org.dante.springboot.security.dto.resp.UserRespDto;
import org.dante.springboot.security.pub.LoginUser;
import org.dante.springboot.security.pub.PageReq;
import org.dante.springboot.security.pub.PageResult;
import org.dante.springboot.security.service.UserService;
import org.dante.springboot.security.util.LoginUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('sysmgr.user.query')")
	@RequestMapping(value = "/query", produces = "application/json")
	public List<User> queryUser(HttpSession session, @RequestBody UserReqDto userReq) {
		LoginUser loginUser = LoginUserUtil.loginUser();
		List<User> users = Lists.newArrayList();
		try {
			/*
			SecurityContext sc = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
			SecAuthUser principal = (SecAuthUser) sc.getAuthentication().getPrincipal();
			*/
			session.setAttribute("session_test_key", new Date().getTime());
			logger.info("当前用户：{}，权限：{}", loginUser, loginUser.getRoles());
			users = userService.findAll();
		} catch (Exception e) {
			logger.error("当前用户：{}，权限：{}", loginUser, loginUser.getRoles(), e);
		}
		return users;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.user.query')")
	@PostMapping(value = "/query_user_list")
	public PageResult<UserRespDto> queryUserPage(PageReq pageReq) {
		PageResult<UserRespDto> result = null;
		try {
			result = userService.finaPage(pageReq);
		} catch (Exception e) {
			logger.error("query_user_list error.", e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.user.query')")
	@PostMapping(value = "/query_by_id", produces = "application/json")
	public BaseResp<UserRespDto> queryByUserId(Long id) {
		BaseResp<UserRespDto> result = new BaseResp<UserRespDto>();
		try {
			UserRespDto userResp = userService.queryById(id);
			result.setData(userResp);
		} catch (Exception e) {
			result.setFlag(false);
			logger.error("queryByUserId userId: {} error.", id, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.user.query')")
	@PostMapping(value = "/query_by_account", produces = "application/json")
	public BaseResp<UserRespDto> queryByAccount(String account) {
		BaseResp<UserRespDto> result = new BaseResp<UserRespDto>();
		try {
			UserRespDto userResp = userService.findByAccount(account);
			result.setData(userResp);
		} catch (Exception e) {
			result.setFlag(false);
			logger.error("queryByUserAccount account: {} error.", account, e);
		}
		return result;
	}

	@PreAuthorize("hasAuthority('sysmgr.user.update')")
	@PostMapping(value = "/update", produces = "application/json")
	public BaseResp<UserRespDto> updateUser(UserReqDto userReqDto) {
		BaseResp<UserRespDto> result = new BaseResp<UserRespDto>();
		try {
			UserRespDto userResp = userService.updateUser(userReqDto);
			result.setData(userResp);
		} catch (Exception e) {
			result.setFlag(false);
			logger.error("updateUser user: {} error.", userReqDto, e);
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('sysmgr.user.delete')")
	@DeleteMapping(value = "/delete_by_id")
	public BaseResp<?> deleteByUserId(Long id) {
		BaseResp<?> result = new BaseResp<>();
		try {
			userService.deleteById(id);
		} catch (Exception e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			logger.error("deleteByUserId userId: {} error.", id, e);
		}
		return result;
	}

}
