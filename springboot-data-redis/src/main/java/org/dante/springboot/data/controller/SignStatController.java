package org.dante.springboot.data.controller;

import org.dante.springboot.data.service.BitMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 利用 BitMap 进行用户签到统计
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/sign")
public class SignStatController {
	
	@Autowired
	private BitMapService bitMapService;
	
	/**
	 * 用户签到
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<Integer> userSign(@PathVariable Long userId) {
		bitMapService.sign(userId);
		return ResponseEntity.ok(1);
	}
	
	/**
	 * 用户补签
	 * 
	 * @param userId
	 * @param signDate, 格式: yyyyMMdd
	 * @return
	 */
	@GetMapping("/resign/{userId}/{signDate}")
	public ResponseEntity<Integer> userSignBackup(@PathVariable Long userId, @PathVariable String signDate) {
		bitMapService.sign(userId, signDate);
		return ResponseEntity.ok(1);
	}
	
	/**
	 * 统计用户总签到天数
	 * 
	 * @param userId
	 * @param yearMonth, 格式: yyyyMM
	 * @return
	 */
	@GetMapping("/stat_all/{userId}/{yearMonth}")
	public ResponseEntity<Integer> signAllCount(@PathVariable Long userId, @PathVariable String yearMonth) {
		return ResponseEntity.ok(bitMapService.signAllCount(userId, yearMonth));
	}
	
	/**
	 * 统计用户连续签到天数
	 * 
	 * @param userId
	 * @param yearMonth, 格式: yyyyMM
	 * @return
	 */
	@GetMapping("/stat_continuous/{userId}/{yearMonth}")
	public ResponseEntity<Integer> signContinuousCount(@PathVariable Long userId, @PathVariable String yearMonth) {
		return ResponseEntity.ok(bitMapService.signContinuousCount(userId, yearMonth));
	}
	
	/**
	 * 获取用户签到月份数据
	 * 
	 * @param userId
	 * @param yearMonth, 格式: yyyyMM
	 * @return
	 */
	@GetMapping("/data/{userId}/{yearMonth}")
	public ResponseEntity<String> signData(@PathVariable Long userId, @PathVariable String yearMonth) {
		return ResponseEntity.ok(bitMapService.getSingData(userId, yearMonth));
	}
	
}
