package org.dante.springboot.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoodsService {
	
	@Autowired
	private EventBusProducer<Goods> bizPublisher;
	
	@Autowired
	private GoodsDAO goodsDAO;

	/**
	 * 同步处理消息
	 * 
	 * @param goods
	 */
	public void saveGoods(Goods goods) {
		// 业务处理
		goodsDAO.saveGoods(goods);
		// 事件发布
		bizPublisher.publishEvent(goods);
		log.info("业务完成 --> {}", goods);
	}
	
	/**
	 * 异步处理消息
	 * 
	 * @param goods
	 */
	public void saveGoodsAsync(Goods goods) {
		// 业务处理
		goodsDAO.saveGoods(goods);
		// 事件发布
		bizPublisher.publishEventAsync(goods);
		
		log.info("业务完成 --> {}", goods);
	}

}
