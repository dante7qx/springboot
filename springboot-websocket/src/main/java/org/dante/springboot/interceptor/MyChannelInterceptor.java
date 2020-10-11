package org.dante.springboot.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyChannelInterceptor implements ChannelInterceptor {

	@Override
	public boolean preReceive(MessageChannel channel) {
		log.info("preReceive --> {} ", channel.toString());
		return ChannelInterceptor.super.preReceive(channel);
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		log.info("postReceive --> {} ", message);
		return ChannelInterceptor.super.postReceive(message, channel);
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		log.info("preSend --> {} ", message);
		return ChannelInterceptor.super.preSend(message, channel);
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		log.info("postSend --> {} ", message);
		ChannelInterceptor.super.postSend(message, channel, sent);
	}

}
