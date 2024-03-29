package org.dante.springboot.service.helloliteflow;

import org.dante.springboot.context.HelloContext;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LiteflowComponent(id = "liteflow", name="LiteFlow组件")
public class LiteFlow extends NodeComponent {
	
	@Override
	public void process() throws Exception {
		log.info("-> 业务【LiteFlow】");
		HelloContext helloContext = this.getContextBean(HelloContext.class);
		helloContext.setMsgDate(DateUtil.date());
		log.info("HelloContext==> {}", helloContext);
	}

}
