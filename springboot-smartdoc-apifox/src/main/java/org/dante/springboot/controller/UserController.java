package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.vo.UserVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

/**
 * 用户管理接口
 * 
 * @apiNote 用户管理相关接口，用于管理用户整个生命周期
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<List<UserVo>> list() {
		return ResponseEntity.ok(Lists.newArrayList(new UserVo()));
	}
	
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<Integer> add(@RequestBody UserVo user) {
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/update")
	public ResponseEntity<Integer> update(@RequestBody UserVo user) {
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 删除用户
	 * 
	 * @param userId 用户Id
	 * @return
	 */
	@PostMapping("/del/{userId}")
	public ResponseEntity<Integer> delete(@PathVariable Long userId) {
		return ResponseEntity.ok().build();
	}
	
}
