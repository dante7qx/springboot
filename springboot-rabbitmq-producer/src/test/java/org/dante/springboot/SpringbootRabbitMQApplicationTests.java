package org.dante.springboot;

import org.dante.springboot.rabbitmq.template.SpiritRabbitPubilsher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitMQApplicationTests {

	@Autowired
	private SpiritRabbitPubilsher spiritRabbitPubilsher;
	
	@Test
	public void directExchange() {
		spiritRabbitPubilsher.sendMessage("aaaa", "bbb", "1111");
	}
}
