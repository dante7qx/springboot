package org.dante.springboot.practice.impl;

import org.dante.springboot.practice.ConsumeModeService;
import org.dante.springboot.practice.IBizService;
import org.dante.springboot.practice.MailEventHandler;
import org.dante.springboot.practice.SmsEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信和邮件系统独立消费（调用handleEventsWith方法把所有消费者实例传进去）
 * 
 * @author dante
 *
 */
@Service("independentModeService")
public class IndependentModeServiceImpl extends ConsumeModeService {
	
	@Autowired
	private IBizService bizService;

	@Override
	protected void disruptorOperate() {
		
		// 调用handleEventsWith，表示创建的多个消费者，每个都是独立消费的
		// 这里创建两个消费者，一个是短信的，一个是邮件的
		disruptor.handleEventsWith(new SmsEventHandler(eventCountPrinter, bizService), new MailEventHandler(eventCountPrinter));

	}

}
