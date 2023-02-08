package org.dante.springboot.mq;

import org.dante.springboot.mq.inner.CleaingEventHandler;
import org.dante.springboot.mq.inner.InnerMQService;
import org.springframework.stereotype.Service;

@Service("testInnerMQService")
public class TestInnerMQServiceImpl extends InnerMQService<Order> {

	@Override
	protected void bizConsume() {
//		innerMQ.handleEventsWith(new TestIndependentComsumer(), new TestIndependentComsumer());
//		innerMQ.handleEventsWithWorkerPool(new TestTogetherConsumer());
//		innerMQ.handleEventsWithWorkerPool(new TestTogetherConsumer(), new TestTogetherConsumer());
		innerMQ.handleEventsWith(new TestIndependentComsumer(), new TestIndependentComsumer())
			.then(new CleaingEventHandler<>());
	}

}
