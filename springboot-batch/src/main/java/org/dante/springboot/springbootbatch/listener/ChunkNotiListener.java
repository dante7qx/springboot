package org.dante.springboot.springbootbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ChunkNotiListener implements ChunkListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChunkNotiListener.class);

	@Override
	public void beforeChunk(ChunkContext context) {
		LOGGER.info("beforeChunk", context.getStepContext().getStepExecutionContext().get("1111"));
		LOGGER.info("beforeChunk", context);
	}

	@Override
	public void afterChunk(ChunkContext context) {
		LOGGER.info("afterChunk ", context);
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		LOGGER.info("afterChunkError ", context);
	}

}
