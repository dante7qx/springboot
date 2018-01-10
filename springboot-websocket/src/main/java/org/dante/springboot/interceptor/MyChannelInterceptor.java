package org.dante.springboot.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyChannelInterceptor extends ChannelInterceptorAdapter {
	
	@Override
	public boolean preReceive(MessageChannel channel) {
		log.info("preReceive --> {} ", channel);
		return super.preReceive(channel);
	}
	
	@Override
	public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
		log.info("afterReceiveCompletion --> {} ", message);
		super.afterReceiveCompletion(message, channel, ex);
	}
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		log.info("preSend --> {} ", message);
		return super.preSend(message, channel);
	}
	
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		log.info("afterSendCompletion --> {} ", message);
		super.afterSendCompletion(message, channel, sent, ex);
	}
	
	
}
