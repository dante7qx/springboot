package org.dante.springboot.practice.impl;

import org.dante.springboot.practice.IBizService;
import org.dante.springboot.practice.StringEvent;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BizServiceImpl implements IBizService {

	@Override
	public void handleBizEvent(StringEvent event) {
		log.info("业务处理事件消息 ==> {}", event);
	}

}
