package org.dante.springboot.springbootbatch.tasklet;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


public class FileDeletingTasklet implements Tasklet {
	
	private Resource directory;
	
	public FileDeletingTasklet(String filePath) {
		this.directory = new FileSystemResource(filePath);
	}


	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File dir = directory.getFile();
        File[] files = dir.listFiles();
        if(files == null) {
			return RepeatStatus.FINISHED;
		}
        for (int i = 0; i < files.length; i++) {
            boolean deleted = files[i].delete();
            if (!deleted) {
                throw new UnexpectedJobExecutionException("Could not delete file " + files[i].getPath());
            }
        }
        dir.deleteOnExit();
        return RepeatStatus.FINISHED;
	}
	
}
