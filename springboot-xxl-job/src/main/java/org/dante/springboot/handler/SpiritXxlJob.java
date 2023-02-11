package org.dante.springboot.handler;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpiritXxlJob {

	/**
	 * 1、简单任务示例（Bean模式）
	 */
	@XxlJob("helloWorldJobHandler")
	public ReturnT<String> helloWorldJobHandler(String param) throws Exception {
		log.info("XXL-JOB, Hello World.");

		for (int i = 0; i < 5; i++) {
			log.info("beat at:" + i);
			TimeUnit.SECONDS.sleep(2);
		}
		return ReturnT.SUCCESS;
	}

	/**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public ReturnT<String> shardingJobHandler(String param) throws Exception {

        // 分片参数
        log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", XxlJobHelper.getShardIndex(), XxlJobHelper.getShardTotal());

        // 业务逻辑
        for (int i = 0; i < XxlJobHelper.getShardTotal(); i++) {
            if (i == XxlJobHelper.getShardIndex()) {
                log.info("第 {} 片, 命中分片开始处理", i);
            } else {
                log.info("第 {} 片, 忽略", i);
            }
        }

        return ReturnT.SUCCESS;
    }
}
