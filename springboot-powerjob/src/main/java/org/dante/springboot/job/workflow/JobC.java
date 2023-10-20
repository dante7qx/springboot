package org.dante.springboot.job.workflow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import cn.hutool.core.lang.Console;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;

@Component
public class JobC implements BasicProcessor {

	@Override
	public ProcessResult process(TaskContext context) throws Exception {
		Console.log("Job C 运行 {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return new ProcessResult(true, "Job C is done");
	}

}
