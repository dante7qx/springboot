package org.dante.springboot.eventbus;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoodsNotifyService {
	
	public void notify(Goods goods) throws Exception {
		if(goods.getId().equals(120L)) {
			throw new Exception("消息处理异常");
		}
		log.info("通知服务 -> {}", goods);
	}

}
