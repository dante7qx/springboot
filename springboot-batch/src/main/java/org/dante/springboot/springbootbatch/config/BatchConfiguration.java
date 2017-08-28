package org.dante.springboot.springbootbatch.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.dante.springboot.springbootbatch.listener.ChunkNotiListener;
import org.dante.springboot.springbootbatch.listener.JobCompletionNotificationListener;
import org.dante.springboot.springbootbatch.po.PersonPO;
import org.dante.springboot.springbootbatch.process.PersonItemProcessor;
import org.dante.springboot.springbootbatch.process.PersonItemProcessor2;
import org.dante.springboot.springbootbatch.tasklet.FileDeletingTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	@Value("/Users/dante/Documents/Project/spring/springboot/springboot-batch/src/main/resources/aa")
	private String deleteFilePath;

	/**
	 * 读取数据
	 * 
	 * @return
	 */
	@Bean
	@StepScope
	public FlatFileItemReader<PersonPO> reader() {
		FlatFileItemReader<PersonPO> reader = new FlatFileItemReader<PersonPO>();
		reader.setResource(new ClassPathResource("sample-data.csv"));
		reader.setLineMapper(new DefaultLineMapper<PersonPO>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "firstName", "lastName" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonPO>() {
					{
						setTargetType(PersonPO.class);
					}
				});
			}
		});
		return reader;
	}

	/**
	 * 处理任务
	 * 
	 * @return
	 */
	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}
	
	/**
	 * 多层处理器组合
	 * 
	 * @return
	 */
	@Bean 
	public CompositeItemProcessor<PersonPO, PersonPO> compositePersonItemProcessor() {
		CompositeItemProcessor<PersonPO, PersonPO> compositePersonItemProcessor = new CompositeItemProcessor<>();
		compositePersonItemProcessor.setDelegates(Arrays.asList(processor(), new PersonItemProcessor2()));
		
		return compositePersonItemProcessor;
	}

	/**
	 * 输出数据
	 * 
	 * @return
	 */
	@Bean
	public JdbcBatchItemWriter<PersonPO> writer() {
		JdbcBatchItemWriter<PersonPO> writer = new JdbcBatchItemWriter<PersonPO>();
		
		// 将文件输出到数据库中
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PersonPO>());
		writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
		writer.setDataSource(dataSource);
		return writer;
	}

	/**
	 * 定义要执行的 job
	 * 
	 * @param listener
	 * @return
	 */
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory
				.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1())
				.next(stepDeleteFilesInDir())
				.end()
				.build();
	}

	/**
	 * 定义实际执行的步骤
	 * 
	 * @return
	 */
	@Bean
	public Step step1() {
		return stepBuilderFactory
				.get("step1")
				.<PersonPO, PersonPO>chunk(10)	// 每次处理的数据 10 条
				.reader(reader())
				.processor(compositePersonItemProcessor())
				.writer(writer())
				.listener(new ChunkNotiListener())
				.build();
	}
	
	@Bean
	public Step stepDeleteFilesInDir() {
		return stepBuilderFactory
				.get("deleteFilesInDir")
				.tasklet(fileDeletingTasklet(deleteFilePath))
				.build();
	}
	
	@Bean 
	public FileDeletingTasklet fileDeletingTasklet(String filePath) {
		return new FileDeletingTasklet(deleteFilePath);
	}

}
