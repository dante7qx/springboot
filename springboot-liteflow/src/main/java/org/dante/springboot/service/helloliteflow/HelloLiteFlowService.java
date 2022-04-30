package org.dante.springboot.service.helloliteflow;

import org.dante.springboot.service.LiteFlowFactory;
import org.dante.springboot.slot.helloliteflow.HelloSlot;
import org.springframework.stereotype.Service;

import com.yomahub.liteflow.entity.data.LiteflowResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloLiteFlowService extends LiteFlowFactory {
	

	public void sayHelloLiteFlow() {
		LiteflowResponse<HelloSlot> resp = flowExecutor.execute2Resp("helloLiteFlowChain", "我是参数", HelloSlot.class);
		log.info("LiteflowResponse<HelloSlot>: {}", resp.getSlot());
	}
	
}
