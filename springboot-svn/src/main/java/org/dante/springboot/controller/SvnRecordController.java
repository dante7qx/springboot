package org.dante.springboot.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.service.ISVNService;
import org.dante.springboot.vo.SvnQueryVO;
import org.dante.springboot.vo.SvnRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;

/**
 * 项目源码记录
 * 
 * @author dante
 *
 */
@Slf4j
@RestController
@RequestMapping("/svn")
public class SvnRecordController {

	@Autowired
	private ISVNService svnService;

	/**
	 * 获取指定svn地址的项目
	 * 
	 * @param query
	 * @return
	 */
	@CrossOrigin(originPatterns = "${server.cross-origin}")
	@PostMapping("/record")
	public ResponseEntity<List<SvnRecordVO>> findSvnRecord(@RequestBody SvnQueryVO query) {
		Assert.notNull(query.getSvnUrl(), "项目SVN地址不能为空！");
		Assert.notNull(query.getStartDate(), "查询开始时间不能为空！");
		log.info("请求参数 => {}", query);
		try {
			StopWatch stopWatch = new StopWatch("SVN代码统计分析");
			stopWatch.start();
			ResponseEntity<List<SvnRecordVO>> result = ResponseEntity.ok(svnService.getRecord(query));
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
			return result;
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}

}
