package org.dante.springboot.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.dante.springboot.service.ISVNService;
import org.dante.springboot.vo.SvnQueryVO;
import org.dante.springboot.vo.SvnRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import cn.hutool.extra.servlet.ServletUtil;
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
	@CrossOrigin(originPatterns = { "http://manager.risun-tec.cn", "http://192.168.1.130:[*]", "http://192.168.1.98:[*]" })
	@PostMapping("/record")
	public ResponseEntity<List<SvnRecordVO>> findSvnRecord(@RequestBody SvnQueryVO query, HttpServletRequest request) {
		Assert.notNull(query.getSvnUrl(), "项目SVN地址不能为空！");
		Assert.notNull(query.getStartDate(), "查询开始时间不能为空！");
		log.info("{} 请求参数 => {}", ServletUtil.getClientIP(request, new String[] {}), query);
		HttpHeaders headers = new HttpHeaders();
		try {
			
			StopWatch stopWatch = new StopWatch("SVN代码统计分析");
			stopWatch.start();
			headers.set("code", HttpStatus.OK + "");
			ResponseEntity<List<SvnRecordVO>> result = ResponseEntity.ok().headers(headers).body(svnService.getRecord(query));
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
			return result;
		} catch (Exception e) {
			headers.set("code", HttpStatus.INTERNAL_SERVER_ERROR + "");
			headers.set("msg", e.getMessage());
			return ResponseEntity.ok().headers(headers).body(Lists.newArrayList());
		}
		
	}

}
