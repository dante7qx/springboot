package org.dante.springboot.job;

import java.util.List;
import java.util.Map;

import org.dante.springboot.vo.SubTask2;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import cn.hutool.core.lang.Console;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.TaskResult;
import tech.powerjob.worker.core.processor.sdk.MapReduceProcessor;

/**
 * MapReduce 处理器
 * 
 * 处理复杂且庞大任务的分布式计算。
 * 通过自己编程的形式，实现 Map 方法，完成任务的切分，再通过 Reduce 汇总子任务结果，即可完成高度可定制的分布式计算。
 * 
 * 通过调用 isRootTask 方法可以判断出当前 Task 是否为根任务，
 * 如果是根任务，则进行任务的切分（PowerJob 支持任意级 map，并不只有在根任务才能切分任务），
 * 然后调用 map 方法分发子任务
 * 
 * "静态分片"
 * 
 * 通过控制台指定分片数量和参数（比如分3片，分片参数为：1=a&2=b&3=c）来控制参与计算的机器数量和起始参数。
 * 
 */
@Component
public class StaticSliceMapReduceJob implements MapReduceProcessor {

	/**
	 * 负责任务的具体执行
	 */
	@Override
	public ProcessResult process(TaskContext context) throws Exception {
		// Root Task 进行任务分发
		if (isRootTask()) {
			Console.log("根任务进行子任务分发。");
			// 从控制台传递分片参数，假设格式为KV：1=a&2=b&3=c
            String jobParams = context.getJobParams();
            // 转化成 Map {1=a, 2=b, 3=c}
            Map<String, String> paramsMap = Splitter.on("&").withKeyValueSeparator("=").split(jobParams);
            
            List<SubTask2> subTasks = Lists.newLinkedList();
            paramsMap.forEach((k, v) -> subTasks.add(new SubTask2(Integer.parseInt(k), v)));
            
            // 调用 map 分发子任务
            map(subTasks, "分片子任务");
            return new ProcessResult(true, "根处理完成");
		}
		
		// 子任务进行处理
		Object obj = context.getSubTask();
		if (obj instanceof SubTask2) {
			SubTask2 subTask = (SubTask2) obj;
			// 业务处理
			Console.log("子任务 {} 处理业务。", subTask.getIndex());
			return new ProcessResult(true, "子任务:" + subTask.getIndex() + " 处理成功");
		}
		
		return new ProcessResult(false, "未知错误！");
	}

	/**
	 * 负责汇集所有子任务得出具体的结果
	 */
	@Override
	public ProcessResult reduce(TaskContext context, List<TaskResult> taskResults) {
		 // 按需求做一些统计工作... 不需要的话，直接使用 Map 处理器即可
		taskResults.forEach(r -> Console.log("{} - {}", r.getTaskId(), r.getResult()));
        return new ProcessResult(true, "xxxx");
	}

}
