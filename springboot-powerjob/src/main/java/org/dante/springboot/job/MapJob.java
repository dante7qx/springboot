package org.dante.springboot.job;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.dante.springboot.service.BizService;
import org.dante.springboot.vo.SubTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import tech.powerjob.common.serialize.JsonUtils;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.MapProcessor;

/**
 * Map处理器 示例
 * 
 */
@Slf4j
@Component
public class MapJob implements MapProcessor {

	@Autowired
	private BizService bizService;

	/**
	 * 每一批发送任务大小
	 */
	private static final int BATCH_SIZE = 10;
	
	/**
	 * 发送的批次
	 */
	private static final int BATCH_NUM = 2;

	@Override
	public ProcessResult process(TaskContext context) throws Exception {
		log.info("============== MapJob 处理 ==============");
		log.info("isRootTask:{}", isRootTask());
		log.info("taskContext:{}", JsonUtils.toJSONString(context));
		log.info("{}", bizService.handleTask());

		if (isRootTask()) {
			log.info("==== MAP ====");
			List<SubTask> subTasks = Lists.newLinkedList();
			for (int j = 0; j < BATCH_NUM; j++) {
				SubTask subTask = new SubTask();
				subTask.setSiteId(j);
				subTask.setItemIds(Lists.newLinkedList());
				subTasks.add(subTask);
				for (int i = 0; i < BATCH_SIZE; i++) {
					subTask.getItemIds().add(i + j * 100);
				}
			}
			map(subTasks, "MAP_TEST_TASK");
			return new ProcessResult(true, "map successfully");
		} else {
			log.info("==== PROCESS ====");
			SubTask subTask = (SubTask) context.getSubTask();
			for (Integer itemId : subTask.getItemIds()) {
				if (Thread.interrupted()) {
					// 任务被中断
					log.info("job has been stop! so stop to process subTask: {} => {}", subTask.getSiteId(), itemId);
					break;
				}
				log.info("处理子任务: {} => {}", subTask.getSiteId(), itemId);
				
				// 休眠500毫秒
				ThreadUtil.sleep(500L);
			}

			// 测试在 Map 任务中追加上下文
            context.getWorkflowContext().appendData2WfContext("Yasuo", "A sword's poor company for a long road.");
            boolean b = ThreadLocalRandom.current().nextBoolean();
            Console.log("随机和重试: {} - {}", b, context.getCurrentRetryTimes());
            if (context.getCurrentRetryTimes() >= 1) {
                // 重试的话一定会成功
                b = true;
            }
            
            return new ProcessResult(b, "RESULT:" + b);
		}
	}

}