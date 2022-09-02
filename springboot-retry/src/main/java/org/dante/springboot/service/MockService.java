package org.dante.springboot.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.vo.Response;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockService {
	
	// 模拟远程服务调用 
    public static Response call() {
        Random rand = new Random();
        int result = rand.nextInt(5);

        if(result == 0) {       // 成功
        	log.info("处理成功");
            return new Response(200, "处理成功");
        } else {
        	ThreadUtil.sleep(1, TimeUnit.SECONDS);
            throw new RuntimeException("处理超过1s，超时");
        }
    }
	
}
