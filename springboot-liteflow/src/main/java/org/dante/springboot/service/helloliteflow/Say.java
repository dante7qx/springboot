package org.dante.springboot.service.helloliteflow;

import org.dante.springboot.context.HelloContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LiteflowComponent(id = "say", name = "快说")
public class Say extends NodeComponent {
	@Override
	public void process() throws Exception {
		log.info("=============================================================== 业务【Say】 ===============================================================");
		HelloContext helloContext = this.getContextBean(HelloContext.class);
		helloContext.setAction("say1");
	}
}
