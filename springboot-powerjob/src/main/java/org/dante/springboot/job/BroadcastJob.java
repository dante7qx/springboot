package org.dante.springboot.job;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.hutool.core.lang.Console;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.TaskResult;
import tech.powerjob.worker.core.processor.sdk.BroadcastProcessor;

/**
 * 广播处理器
 * 
 * 广播执行的策略下，所有机器都会被调度执行该任务。
 * 为了便于资源的准备和释放，广播处理器在BasicProcessor 的基础上额外增加了 preProcess 和 postProcess 方法，
 * 分别在整个集群开始之前/结束之后选一台机器执行相关方法
 * 
 */
@Component
public class BroadcastJob implements BroadcastProcessor {

	/**
     * 在所有节点广播执行前执行，只会在一台机器执行一次
     */
	@Override
	public ProcessResult preProcess(TaskContext context) throws Exception {
		// 预执行，会在所有 worker 执行 process 方法前调用
		Console.log("BroadcastJob start to process, Job Id is {}, Instance Id is {}.", context.getJobId(), context.getInstanceId());
        return new ProcessResult(true, "init success");
	}

	/**
     * 在所有节点都会执行一次
     */
	@Override
	public ProcessResult process(TaskContext context) throws Exception {
		// 撰写整个worker集群都会执行的代码逻辑
		Console.log("BroadcastJob start to process, Job Id is {}, Instance Id is {}.", context.getJobId(), context.getInstanceId());
        return new ProcessResult(true, "release resource success");
	}

	/**
     * 在所有节点广播执行完成后执行，只会在一台机器执行一次
     */
	@Override
	public ProcessResult postProcess(TaskContext context, List<TaskResult> taskResults) throws Exception {
		// taskResults 存储了所有worker执行的结果（包括preProcess）

        // 收尾，会在所有 worker 执行完毕 process 方法后调用，该结果将作为最终的执行结果
		Console.log("BroadcastJob start to process, Job Id is {}, Instance Id is {}.", context.getJobId(), context.getInstanceId());
        return new ProcessResult(true, "process success");
	}

}
