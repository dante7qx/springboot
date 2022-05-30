package org.dante.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.FlowExecutor;

@Component
public class LiteFlowFactory {

	@Autowired
	protected FlowExecutor flowExecutor;

}
