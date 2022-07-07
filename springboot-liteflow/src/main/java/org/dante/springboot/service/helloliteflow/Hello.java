package org.dante.springboot.service.helloliteflow;

import org.dante.springboot.context.HelloContext;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LiteflowComponent("hello")
public class Hello extends NodeComponent {

	/**
	 * 表示是否进入该节点，可以用于业务参数的预先判断
	 */
	@Override
	public boolean isAccess() {
		HelloContext helloContext = this.getContextBean(HelloContext.class);
		return "say".equalsIgnoreCase(helloContext.getAction()); 
	}
	
	@Override
	public void process() throws Exception {
		log.info("-> 业务【Hello】");
		// 获取流程入参，执行流程的第二个参数。
		// 如果你把数据上下文的实例传入了，并不意味着你拿到的相同类型的数据上下文中就是有值的。因为这2个对象根本就是2个实例。 流程入参只能通过this.getRequestData()去拿
		String arg = this.getRequestData();
		log.info("你好，{}", arg);
		
		HelloContext helloContext = this.getContextBean(HelloContext.class);
		helloContext.setMsgId(IdUtil.nanoId(16));
		helloContext.setReqArg(arg);
		log.info("HelloContext==> {}", helloContext);
	}

}
