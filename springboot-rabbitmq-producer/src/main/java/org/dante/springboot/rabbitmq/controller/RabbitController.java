package org.dante.springboot.rabbitmq.controller;

import org.dante.springboot.rabbitmq.prop.SpiritRabbitProperties;
import org.dante.springboot.rabbitmq.template.SpiritRabbitAckPublisher;
import org.dante.springboot.rabbitmq.template.SpiritRabbitPubilsher;
import org.dante.springboot.rabbitmq.vo.Msg;
import org.dante.springboot.rabbitmq.vo.PubMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;


@RestController
public class RabbitController {
	
	@Autowired
	private SpiritRabbitPubilsher spiritRabbitPubilsher;
	@Autowired
	private SpiritRabbitAckPublisher spiritRabbitAckPublisher;
	@Autowired
	private SpiritRabbitProperties spiritRabbitProperties;
	
	@GetMapping("/send/{flag}")
	public void send(@PathVariable Integer flag) {
		PubMsg pubMsg = buildMsg();
		switch (flag) {
		case 0:
			spiritRabbitPubilsher.sendMessage(spiritRabbitProperties.getDefaultQueue(), pubMsg);
			spiritRabbitAckPublisher.sendMessage(spiritRabbitProperties.getAckQueue(), pubMsg);
			break;
		case 1:
			spiritRabbitPubilsher.sendMessage(spiritRabbitProperties.getDirectExchange(), spiritRabbitProperties.getDirectRoutingKey(), pubMsg);
			spiritRabbitAckPublisher.sendMessage(spiritRabbitProperties.getDirectExchange(), spiritRabbitProperties.getDirectRoutingKey(), pubMsg);
			break;
		case 2:
			spiritRabbitPubilsher.sendMessage(spiritRabbitProperties.getFanoutExchange(), "", pubMsg);
			break;
		case 3:
			spiritRabbitPubilsher.sendMessage(spiritRabbitProperties.getTopicExchange(), "x."+spiritRabbitProperties.getTopicRoutingKey()+".y", pubMsg);
			spiritRabbitPubilsher.sendMessage(spiritRabbitProperties.getTopicExchange(), "mq.x."+spiritRabbitProperties.getTopicRoutingKey()+".y.log", pubMsg);
			break;
		default:
			break;
		}
	}
	
	private PubMsg buildMsg() {
		PubMsg pm = new PubMsg();
		pm.setMsgId("111111111111");
		pm.setMsgDesc("消息描述");
		Msg msg1 = new Msg();
		msg1.setAge(20);
		msg1.setName("消息1");
		Msg msg2 = new Msg();
		msg2.setAge(30);
		msg2.setName("消息2");
		pm.setPayload(Lists.newArrayList(msg1, msg2));
		return pm;
	}
}
