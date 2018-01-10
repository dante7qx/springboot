package org.dante.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class MessageService {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	int status = 1;

	@Scheduled(cron = "0/3 * * * * ?")
	public void send() {
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
		simpMessagingTemplate.convertAndSend("/topic/status_change", status);
	}
}
