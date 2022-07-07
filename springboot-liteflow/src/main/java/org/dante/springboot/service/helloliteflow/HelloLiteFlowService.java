package org.dante.springboot.service.helloliteflow;

import org.dante.springboot.context.HelloContext;
import org.dante.springboot.service.LiteFlowFactory;
import org.springframework.stereotype.Service;

import com.yomahub.liteflow.flow.LiteflowResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloLiteFlowService extends LiteFlowFactory {
	

	public void sayHelloLiteFlow() throws Exception {
		LiteflowResponse resp = flowExecutor.execute2Resp("helloLiteFlowChain", "orderId: 100", HelloContext.class);
		// 流程执行是否成功
		boolean isSuccess = resp.isSuccess();
		if(isSuccess) {
			// 获得执行步骤详细信息
			resp.getExecuteSteps();
			// 获得步骤字符串信息（组件ID[组件别名]<耗时毫秒>）
			resp.getExecuteStepStr();
			// 上下文参数
			HelloContext helloContext = resp.getContextBean(HelloContext.class);
			log.info("HelloContext==> {}", helloContext);
		} else {
			throw new Exception(resp.getCause());
		}
		log.info("=============================================================== 流程结束 ===============================================================");
	}
	
}
