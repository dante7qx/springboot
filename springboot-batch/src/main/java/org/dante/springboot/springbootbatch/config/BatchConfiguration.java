package org.dante.springboot.springbootbatch.config;

import javax.sql.DataSource;

import org.dante.springboot.springbootbatch.listener.JobCompletionNotificationListener;
import org.dante.springboot.springbootbatch.po.PersonPO;
import org.dante.springboot.springbootbatch.process.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * 读取数据
	 * 
	 * @return
	 */
	@Bean
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
				.processor(processor())
				.writer(writer())
				.build();
	}

}
