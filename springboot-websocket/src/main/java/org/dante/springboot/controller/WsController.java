package org.dante.springboot.controller;

import java.util.Random;

import org.dante.springboot.request.MessageRequest;
import org.dante.springboot.response.MessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WsController {

	/**
	 * 客户端发送请求的URL，通过 @MessageMapping 映射
	 * @SendTo, 即destination。消息订阅的路径，服务器向订阅了此路径的客户端发送消息
	 * 
	 * @param request
	 * @return
	 */
	@MessageMapping("/hello")
	@SendTo("/topic/get_msg_resp")
	public MessageResponse sendMsgResp(MessageRequest request) {
		log.info("收到消息 {}.", request);
		return new MessageResponse("消息[" + request.getName() + "]，序号" + new Random().nextInt(9) + 1);
	}
	
	int status = 1;
	
	@MessageMapping("/status")
	@SendTo("/topic/status_change")
	public int serveStatus() {
		int oldStatus = status;
		switch (status) {
		case 1:
			status = 2;
			break;
		case 2:
			status = 3;
			break;
		case 3:
			status = 1;
			break;
		default:
			break;
		}
		log.info("Status change from {} to {}.", oldStatus, status);
		return status;
	}
	
}
