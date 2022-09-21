package org.dante.springboot.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.service.HelloService;
import org.dante.springboot.vo.Response;
import org.springframework.stereotype.Service;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public Response call() {
		Random rand = new Random();
        int result = rand.nextInt(5);
        if(result == 0) {       // 成功
        	log.info("处理成功");
            return new Response(200, "处理成功");
        } else {
        	log.info("处理失败");
        	ThreadUtil.sleep(1, TimeUnit.SECONDS);
            throw new RuntimeException("处理超过1s，超时");
        }
	}

}
