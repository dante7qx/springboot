package org.dante.demo.amqp;

import java.util.List;

import org.dante.demo.amqp.constant.RabbitMQConstant;
import org.dante.demo.amqp.dto.req.Data;
import org.dante.demo.amqp.dto.req.DataDto;
import org.dante.demo.amqp.hello.ConfirmSender;
import org.dante.demo.amqp.hello.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootAmqpApplicationTests {
	
	private final static Logger logger = LoggerFactory.getLogger(SpringbootAmqpApplicationTests.class);
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	@Autowired
	private Sender sender;
	@Autowired
	private ConfirmSender confirmSender;
	
	@Test
	public void sendHelloMsg() {
		DataDto dto = new DataDto();
		dto.setId(100);
		dto.setSearch("ZBAA");
		dto.setEnable(false);
		
		List<Data> datas = Lists.newArrayList();
		Data data = new Data();
		data.setName("测试");
		data.setAge(30);
		datas.add(data);
		dto.setDatas(datas);
		sender.sendMessage(dto);
	}
	
	@Test
	public void sendConfirmMsg() {
		confirmSender.sendMsg("测试RabbitMQ消息的ack应答模式");
	}
	
	@Test
	public void receiveHelloMsg() {
		rabbitTemplate.receiveAndReply(RabbitMQConstant.HELLO_QUEUE, new ReceiveAndReplyCallback() {
			@Override
			public Object handle(Object payload) {
				logger.info("收到消息 ==========================> " + payload);
				return null;
			}
			
		});
	}
	
}
