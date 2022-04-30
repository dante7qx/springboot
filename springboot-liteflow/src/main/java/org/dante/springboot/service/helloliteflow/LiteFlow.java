package org.dante.springboot.service.helloliteflow;

import org.dante.springboot.slot.helloliteflow.HelloSlot;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LiteflowComponent(id = "liteflow", name="LiteFlow组件")
public class LiteFlow extends NodeComponent {

	@Override
	public void process() throws Exception {
		log.info("LiteFlow，轻量，快速，稳定可编排的组件式规则引擎。");
		HelloSlot slot = this.getSlot();
		slot.setMsgDate(DateUtil.date());
	}

}
