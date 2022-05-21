package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.consts.GroupEnum;
import org.dante.springboot.util.BaiduFaceUtil;
import org.dante.springboot.vo.CheckFaceVO;
import org.dante.springboot.vo.RegisterUserVO;
import org.dante.springboot.vo.resp.RegisterRespVO;
import org.dante.springboot.vo.resp.RespVO;
import org.dante.springboot.vo.resp.UserFaceRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/baidu/face")
public class BaiduFaceAIController {

	@Autowired
	private BaiduFaceUtil baiduFaceUtil;
	
	@PostMapping("/register")
	public ResponseEntity<RespVO<RegisterRespVO>> register(@RequestBody RegisterUserVO userVO) {
		log.info("注册人员：{}", userVO);
		ResponseEntity<RespVO<RegisterRespVO>> response = null;
		try {
			response = ResponseEntity.ok(baiduFaceUtil.register(userVO));
		} catch (Exception e) {
			response = ResponseEntity.internalServerError().build();
			log.error(e.getMessage(), e);
		}
		return response;
	}
	
	@PostMapping("/user_list")
	public ResponseEntity<RespVO<List<String>>> userList() {
		String groupId = GroupEnum.CN_GRP.id();
		log.info("用户列表：{}", groupId);
		ResponseEntity<RespVO<List<String>>> response = null;
		try {
			response = ResponseEntity.ok(baiduFaceUtil.getGroupUsers(groupId));
		} catch (Exception e) {
			response = ResponseEntity.internalServerError().build();
			log.error(e.getMessage(), e);
		}
		return response;
	}
	
	@PostMapping("/user_face/{userId}")
	public ResponseEntity<RespVO<List<UserFaceRespVO>>> userFaceList(@PathVariable String userId) {
		String groupId = GroupEnum.CN_GRP.id();
		log.info("用户 {} 人脸列表", userId);
		ResponseEntity<RespVO<List<UserFaceRespVO>>> response = null;
		try {
			response = ResponseEntity.ok(baiduFaceUtil.getUserFaces(userId, groupId));
		} catch (Exception e) {
			response = ResponseEntity.internalServerError().build();
			log.error(e.getMessage(), e);
		}
		return response;
	}
	
	@PostMapping("/check")
	public String checkFace(@RequestBody CheckFaceVO vo) {
		log.info("人脸检测：{}", vo);
		String result = "";
		try {
			result = baiduFaceUtil.faceDetect(vo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
}
