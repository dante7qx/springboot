package org.dante.springboot.utmost;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LimitsOfRequestController {
	
	@GetMapping("/get_test")
    public void getTest(@RequestParam int num) throws Exception {
        log.info("{} 接受到请求:num={}", Thread.currentThread().getName(), num);
        TimeUnit.HOURS.sleep(1);
    }
	
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
            int finalI = i;
            new Thread(() -> {
                HttpUtil.get("127.0.0.1:8100/get_test?num=" + finalI);
            }).start();
        }
        //阻塞主线程
        Thread.yield();
	}
	
}
