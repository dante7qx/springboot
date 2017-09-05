package org.dante.springboot.springbootbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Step 执行上下文 Listener
 * 
 * @author dante
 *
 */
@Component
public class Step1Listener implements StepExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(Step1Listener.class);
	
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext ec = stepExecution.getExecutionContext();
		ec.put("1111", "--------111222--------");
		LOGGER.info("beforeStep: " + stepExecution.getStatus());
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOGGER.info("afterStep: " + stepExecution.getStatus());
		return null;
	}

}
