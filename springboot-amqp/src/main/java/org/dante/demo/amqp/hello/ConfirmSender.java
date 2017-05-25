package org.dante.demo.amqp.hello;

import java.util.UUID;

import org.dante.demo.amqp.constant.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfirmSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

	private final static Logger logger = LoggerFactory.getLogger(ConfirmSender.class);
	private RabbitTemplate rabbitTemplate;

	@Autowired
	public ConfirmSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(this); // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
		rabbitTemplate.setReturnCallback(this); // rabbitTemplate需要注册ReturnCallback
		// rabbitTemplate.setReplyAddress(RabbitMQConstant.CONFIRM_QUEUE);
		// rabbitTemplate.setReplyTimeout(6000);
		rabbitTemplate.setUseTemporaryReplyQueues(true);
	}

	public void sendMsg(String content) {
		CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
		Object returnObj = rabbitTemplate.convertSendAndReceive(RabbitMQConstant.CONFIRM_EXCHANGE,
				RabbitMQConstant.CONFIRM_ROUTING_KEY, content, correlationId);
		logger.info("消息返回：" + returnObj);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		logger.info("回调Id：" + correlationData.getId());
		if (ack) {
			logger.info("消息成功消费");
		} else {
			logger.error("消息消费失败，原因：{}", cause);
		}

	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		logger.info("message: " + message);
		logger.info("replyCode: {},replyText: {},exchange: {},routingKey: {}", replyCode, replyText, exchange,
				routingKey);
	}

}
