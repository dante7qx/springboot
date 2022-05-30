package org.dante.springboot.service.helloliteflow;

import org.dante.springboot.slot.helloliteflow.HelloSlot;
import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeComponent;

import cn.hutool.core.lang.ObjectId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("hello")
public class Hello extends NodeComponent {

	@Override
	public void process() throws Exception {
		HelloSlot slot = this.getSlot();
		log.info("你好，{}", slot.getRequestData().toString());
		slot.setMsgId(ObjectId.next());
	}

}
