package org.dante.demo.swagger.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dante.demo.swagger.vo.UserVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "User Manger API", description="用户管理API")
@RestController
@RequestMapping("/user")
public class UserController {

	@ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
	@GetMapping("/get/{id}")
	public UserVO getUser(@ApiParam(value = "用户Id", required = true) 
							@PathVariable Long id) {
		UserVO userVO = new UserVO(id, "get", 23.7,
				DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
		return userVO;
	}

	@ApiOperation(value = "获取用户列表", notes = "获取用户列表")
	@PostMapping(value = "/post", produces = "application/json")
	public UserVO postUser(@RequestBody UserVO reqUser) {
		UserVO userVO = new UserVO(50L, "post", 23.7,
				DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
		return userVO;
	}

	@ApiOperation(value = "保存用户信息")
	@PutMapping("/put")
	public UserVO putUser(@RequestBody UserVO userVO) {
		UserVO user = new UserVO(3L, "put", 23.7,
				DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
		return user;
	}

	@ApiOperation(value = "删除用户信息")
	@DeleteMapping("/delete")
	public UserVO deleteUser(@RequestBody UserVO reqUser) {
		UserVO userVO = new UserVO(4L, "delete", 23.7,
				DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
		return userVO;
	}

}
