package org.dante.springboot.service;

import java.time.LocalTime;

import org.dante.springboot.util.RetryBackOffUtil;
import org.dante.springboot.util.RetryPolicyUtil;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用RetryTemplate来进行重试
 */
@Slf4j
@Service
public class RetryPolicyService {
	
	private int totalNum = 3;

    /**
     * 使用RetryTemplate进行重试
     * @param num
     * @return
     * @throws Throwable
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public int baseRetryTemplate(int num) throws Throwable {
        RetryTemplate template = RetryTemplate.builder().retryOn(Exception.class).build();
        // 设置退避策略
        template.setBackOffPolicy(RetryBackOffUtil.getFixedBackOffPolicy());
        // 设置重试策略
        template.setRetryPolicy(RetryPolicyUtil.getSimpleRetryPolicy());
        // 设置执行方法
        template.execute(new RetryCallback<Object, Throwable>() {
            @Override
            public Object doWithRetry(RetryContext context) throws Throwable {
                return baseRetry(num);
            }
        }, new RecoveryCallback() {
            @Override
            public Object recover(RetryContext context) throws Exception {
                baseRetryRecover();
                return totalNum;
            }
        });
        return totalNum - num;
    }

    /**
     * 执行业务
     * @param num
     * @return
     * @throws Exception
     */
    public int baseRetry(Integer num) throws Exception {
        log.info("baseRetry开始执行业务" + LocalTime.now());
        totalNum = totalNum + num;
        if (totalNum >= 0) {
            log.error("执行任务失败,数据为：{}",totalNum);
            throw new IllegalArgumentException("执行任务失败,数据为：" + totalNum);
        }
        log.info("baseRetry业务执行结束" + LocalTime.now());
        return totalNum - num;
    }

    /**
     * 重试失败后的兜底方法
     * @return
     */
    public int baseRetryRecover() {
        log.warn("业务执行失败,进入Recover！！！" + LocalTime.now());
        return totalNum;
    }
	
}
