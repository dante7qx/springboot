package org.dante.springboot.job;

import org.dante.springboot.service.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hutool.core.lang.Console;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;

/**
 * 单机处理器
 * 
 * 单机处理器（BasicProcessor）对应了单机任务，即某个任务的某次运行只会有某一台机器的某一个线程参与运算。
 * 
 */
@Component
public class BasicJob implements BasicProcessor {
	
	@Autowired
	private BizService bizService;

	@Override
	public ProcessResult process(TaskContext context) throws Exception {
		
		
		Console.log("BasicJob start to process, current JobParams is {}.", context.getJobParams());
		
        // 在线日志功能，可以直接在控制台查看任务日志，非常便捷
        OmsLogger omsLogger = context.getOmsLogger();
        omsLogger.info("BasicJob start to process, current JobParams is {}.", context.getJobParams());
        
		// TaskContext为任务的上下文信息，包含了在控制台录入的任务元数据，常用字段为
        // jobParams（任务参数，在控制台录入），instanceParams（任务实例参数，通过 OpenAPI 触发的任务实例才可能存在该参数）
		
		// 业务逻辑
		bizService.handleTask();
		
		// 返回结果，该结果会被持久化到数据库，在前端页面直接查看，极为方便
        return new ProcessResult(true, "result is done");
	}

}
