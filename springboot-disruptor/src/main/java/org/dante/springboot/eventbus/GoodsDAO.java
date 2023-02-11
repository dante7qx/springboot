package org.dante.springboot.eventbus;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GoodsDAO {

	public void saveGoods(Goods goods) {
		log.info("业务保存 --> {}", goods);
	}
}
