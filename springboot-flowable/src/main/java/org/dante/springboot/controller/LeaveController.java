package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dto.LeaveDTO;
import org.dante.springboot.po.LeavePO;
import org.dante.springboot.service.ILeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/leave")
public class LeaveController {

	@Autowired
	private ILeaveService leaveService;

	/**
	 * 获取请假列表
	 * 
	 * @return
	 */
	@GetMapping("/query_list/{userId}")
	public List<LeavePO> queryList(@PathVariable String userId) {
		log.info("用户Id：{} ", userId);
		return null;
	}

	@PostMapping("/add")
	@ResponseBody
	public LeavePO addLeave(@RequestBody LeaveDTO leaveDTO) {
		LeavePO leavePO = null;
		try {
			leavePO = leaveService.saveLeave(leaveDTO);
		} catch (Exception e) {
			log.error("请假申请失败.", e);
		}
		return leavePO;

	}

}
