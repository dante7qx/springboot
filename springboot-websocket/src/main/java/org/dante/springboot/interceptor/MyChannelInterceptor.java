package org.dante.springboot.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyChannelInterceptor implements ChannelInterceptor {
	
	@Override
	public boolean preReceive(MessageChannel channel) {
		log.info("preReceive --> {} ", channel);
		return true;
	}
	
	@Override
	public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
		log.info("afterReceiveCompletion --> {} ", message);
	}
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		log.info("preSend --> {} ", message);
		return message;
	}
	
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		log.info("afterSendCompletion --> {} ", message);
	}
	
	
}
