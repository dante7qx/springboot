package org.dante.springboot.practice.impl;

import org.dante.springboot.practice.ConsumeModeService;
import org.dante.springboot.practice.MailEventHandler;
import org.dante.springboot.practice.MailWorkEventHandler;
import org.springframework.stereotype.Service;

/**
 * 组合消费
 * 
 * @author dante
 *
 */
@Service("combineUseService")
public class CombineUseServiceImpl extends ConsumeModeService {

	@Override
	protected void disruptorOperate() {
		MailWorkEventHandler c1 = new MailWorkEventHandler(eventCountPrinter);
		MailWorkEventHandler c2 = new MailWorkEventHandler(eventCountPrinter);
		MailEventHandler c3 = new MailEventHandler(eventCountPrinter);
		MailEventHandler c4 = new MailEventHandler(eventCountPrinter);
		MailEventHandler c5 = new MailEventHandler(eventCountPrinter);
		MailEventHandler c6 = new MailEventHandler(eventCountPrinter);
		MailEventHandler c7 = new MailEventHandler(eventCountPrinter);

		// C1和C2共同消费，C3和C4独立消费，但C3和C4都依赖C1和C2，然后C5和C6依赖C3和C4
		disruptor.handleEventsWithWorkerPool(c1, c2)
			.then(c3, c4)
			.then(c5, c6)
			.then(c7);

		// C3、C4独立消费，C5依赖C3和C4
//		disruptor
//			.handleEventsWith(c3, c4)
//			.then(c5);

		// C3独立消费，C4和C5也独立消费，但依赖C3，C6依赖C4和C5
//		disruptor
//			.handleEventsWith(c3)
//			.then(c4, c5)
//			.then(c6);

		// C3和C4独立消费，C5和C6也是独立消费，但C5和C6都依赖C3和C4，然后C7依赖C5和C6
//		disruptor
//			.handleEventsWith(c3, c4)
//			.then(c5, c6)
//			.then(c7);

		// C1和C2共同消费，C11和C22也是共同消费，但C11和C22都依赖C1和C2，然后C33依赖C11和C22
//		MailWorkEventHandler c11 = new MailWorkEventHandler(eventCountPrinter);
//		MailWorkEventHandler c22 = new MailWorkEventHandler(eventCountPrinter);
//		MailWorkEventHandler c33 = new MailWorkEventHandler(eventCountPrinter);
//		disruptor
//			.handleEventsWithWorkerPool(c1, c2)
//			.thenHandleEventsWithWorkerPool(c11, c22)
//			.thenHandleEventsWithWorkerPool(c33);

	}

}
