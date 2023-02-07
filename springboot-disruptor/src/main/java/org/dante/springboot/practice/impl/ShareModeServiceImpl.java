package org.dante.springboot.practice.impl;

import org.dante.springboot.practice.ConsumeModeService;
import org.dante.springboot.practice.MailWorkEventHandler;
import org.springframework.stereotype.Service;

/**
 * 邮件系统的两个邮件服务器共同消费（调用handleEventsWithWorkerPool方法，把共同消费的MailWorkHandler实例作为参数传入）
 * 
 * @author dante
 *
 */
@Service("shareModeService")
public class ShareModeServiceImpl extends ConsumeModeService {

	@Override
	protected void disruptorOperate() {
		// mailWorkHandler1模拟一号邮件服务器
        MailWorkEventHandler mailWorkHandler1 = new MailWorkEventHandler(eventCountPrinter);

        // mailWorkHandler2模拟一号邮件服务器
        MailWorkEventHandler mailWorkHandler2 = new MailWorkEventHandler(eventCountPrinter);
        
        // 调用handleEventsWithWorkerPool，表示创建的多个消费者以共同消费的模式消费
        disruptor.handleEventsWithWorkerPool(mailWorkHandler1, mailWorkHandler2);
	}

}
