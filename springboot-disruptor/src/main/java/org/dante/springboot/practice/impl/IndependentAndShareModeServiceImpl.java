package org.dante.springboot.practice.impl;

import org.dante.springboot.practice.ConsumeModeService;
import org.dante.springboot.practice.IBizService;
import org.dante.springboot.practice.MailWorkEventHandler;
import org.dante.springboot.practice.SmsEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信系统独立消费，与此同时，两个邮件服务器共同消费
 * 
 * @author dante
 *
 */
@Service("independentAndShareModeService")
public class IndependentAndShareModeServiceImpl extends ConsumeModeService {
	
	@Autowired
	private IBizService bizService;

	@Override
	protected void disruptorOperate() {
		// 调用handleEventsWith，表示创建的多个消费者，每个都是独立消费的
		// 这里创建两个消费者，一个是短信的，一个是邮件的
		disruptor.handleEventsWith(new SmsEventHandler(eventCountPrinter, bizService));
		
		// mailWorkHandler1模拟一号邮件服务器
        MailWorkEventHandler mailWorkHandler1 = new MailWorkEventHandler(eventCountPrinter);
        // mailWorkHandler2模拟一号邮件服务器
        MailWorkEventHandler mailWorkHandler2 = new MailWorkEventHandler(eventCountPrinter);
        // 调用handleEventsWithWorkerPool，表示创建的多个消费者以共同消费的模式消费
        disruptor.handleEventsWithWorkerPool(mailWorkHandler1, mailWorkHandler2);
	}

}
