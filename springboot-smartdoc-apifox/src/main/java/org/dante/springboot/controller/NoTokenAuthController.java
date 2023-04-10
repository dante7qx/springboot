package org.dante.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 无需 Token 认证接口
 * 
 * @author dante
 *
 */
@RestController
public class NoTokenAuthController {
	
	/**
	 * 用户登录
	 * 
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<Integer> login() {
		return ResponseEntity.ok(200);
	}
	
	/**
	 * 请假列表
	 * 
	 * @return
	 */
	@PostMapping("/api/leave/list")
	public ResponseEntity<Integer> list() {
		return ResponseEntity.ok(200);
	}
	
	/**
	 * 新增请假申请
	 * 
	 * @return
	 */
	@PostMapping("/api/leave/add")
	public ResponseEntity<Integer> add() {
		return ResponseEntity.ok(200);
	}
	
}
