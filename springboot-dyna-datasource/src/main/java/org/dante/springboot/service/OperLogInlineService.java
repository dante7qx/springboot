package org.dante.springboot.service;

import java.util.Date;
import java.util.List;

import org.dante.springboot.mapper.OperLogInlineMapper;
import org.dante.springboot.vo.OperLogInline;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OperLogInlineService {
	
	private final OperLogInlineMapper operLogMapper;
	
	@Transactional
	public int insertOperlog(int count) {
		int result = 0;
		Date startDate = DateUtil.parse("2021-01-01 00:00:00", DatePattern.NORM_DATETIME_PATTERN);
		for (int i = 1; i <= count; i++) {
			OperLogInline log = new OperLogInline();
			log.setUserId(Long.valueOf(i));
			log.setTitle("标题-" + i);
			log.setBusinessType(1);
			log.setMethod("org.dante.springboot.service.OperLogService.batchInsertOperlog");
			log.setRequestMethod(HttpMethod.POST.name());
			log.setOperatorType(1);
			log.setOperName("dante");
			log.setDeptName("Java研发部");
			log.setOperUrl("/oper_log/batch_add");
			log.setOperIp("127.0.0.1");
			log.setOperLocation("天水");
			log.setOperParam("{param: " + i + "}");
			log.setJsonResult("{result: " + i + "}");
			log.setStatus(0);
			if(i == 1) {
				log.setOperTime(startDate);
			} else {
				log.setOperTime(DateUtil.offsetSecond(startDate, 86400 * i));
			}
			result += operLogMapper.insertOperlogInline(log);
		}
		return result;
	}
	
	@Transactional
	public int batchInsertOperlog(int count) {
		Date startDate = DateUtil.parse("2021-01-01 00:00:00", DatePattern.NORM_DATETIME_PATTERN);
		List<OperLogInline> operLogs = Lists.newArrayList();
		for (int i = 1; i <= count; i++) {
			OperLogInline log = new OperLogInline();
			log.setUserId(Long.valueOf(i));
			log.setTitle("标题-" + i);
			log.setBusinessType(1);
			log.setMethod("org.dante.springboot.service.OperLogService.batchInsertOperlog");
			log.setRequestMethod(HttpMethod.POST.name());
			log.setOperatorType(1);
			log.setOperName("dante");
			log.setDeptName("Java研发部");
			log.setOperUrl("/oper_log/batch_add");
			log.setOperIp("127.0.0.1");
			log.setOperLocation("天水");
			log.setOperParam("{param: " + i + "}");
			log.setJsonResult("{result: " + i + "}");
			log.setStatus(0);
			if(i == 1) {
				log.setOperTime(startDate);
			} else {
				log.setOperTime(DateUtil.offsetSecond(startDate, 86400 * i));
			}
			operLogs.add(log);
		}
		operLogMapper.batchInsertOperlogInline(operLogs);
		return count;
	}
	
	@DS("slave_1")
	public List<OperLogInline> selectOperLogList(OperLogInline operLog) {
		return operLogMapper.selectOperLogInlineList(operLog);
	}
	
	public void cleanOperLog() {
		operLogMapper.cleanOperLogInline();
	}
	
}
