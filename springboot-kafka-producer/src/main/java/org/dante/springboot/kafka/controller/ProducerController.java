package org.dante.springboot.kafka.controller;

import org.dante.springboot.kafka.service.KafkaProducer;
import org.dante.springboot.kafka.vo.Msg;
import org.dante.springboot.kafka.vo.PubMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProducerController {
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@GetMapping("/send/{msg}")
	public PubMsg send(@PathVariable String msg) {
		String topic = "topic.spirit.msg";
		for (int i = 0; i < 100; i++) {
			PubMsg pubMsg = buildMsg(msg + "-" + i);
			log.info("kafka的分区消息={}", pubMsg);
			
			kafkaProducer.sendMsg(topic, pubMsg);
		}
		return buildMsg(msg);
	}
	
	@GetMapping("/psend/{msg}")
	public PubMsg psend(@PathVariable String msg) {
		String topic = "SPIRIT_P_M_";
		for (int i = 0; i < 100; i++) {
			PubMsg pubMsg = buildMsg(msg + "-" + i);
			log.info("kafka的多主题消息={}", pubMsg);
			
			if( i % 5 == 0) {
				topic = topic.concat(String.valueOf(i));
			}
			kafkaProducer.sendMsg(topic, pubMsg);
		}
		return buildMsg(msg);
	}
	
	private PubMsg buildMsg(String msgId) {
		PubMsg pm = new PubMsg();
		pm.setMsgId(msgId);
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
