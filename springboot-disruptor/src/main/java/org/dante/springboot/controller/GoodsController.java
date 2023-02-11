package org.dante.springboot.controller;

import org.dante.springboot.eventbus.Goods;
import org.dante.springboot.eventbus.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 参考资料：https://juejin.cn/post/6864940197269667853#heading-5
 * 
 * @author dante
 *
 */
@Slf4j
@RestController
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	@PostMapping("/addGoods")
	public Goods addGoods(@RequestBody Goods goods) {
		log.info("====================================> 同步EventBus");
		goodsService.saveGoods(goods);
		return goods;
	}
	
	@PostMapping("/addGoodsAsync")
	public Goods addGoodsAsync(@RequestBody Goods goods) {
		log.info("====================================> 异步EventBus");
		goodsService.saveGoodsAsync(goods);
		return goods;
	}
	
}
