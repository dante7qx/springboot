package org.dante.springboot.config;

import org.dante.springboot.interceptor.MyChannelInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @EnableWebSocketMessageBroker 开启使用 STOMP 协议来处理 Message broker 的消息（@MessageMapping）
 * 
 * @author dante
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new MyChannelInterceptor());
	}

	/**
	 * STOMP 协议节点，指定客户端使用 SockJS 协议进行连接 
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/hello_websocket", "/chat_room").withSockJS(); 
	}

	/**
	 * 配置广播式 Topic 和 点对点 queue 的消息代理
	 * 
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/queue");
	}
}
