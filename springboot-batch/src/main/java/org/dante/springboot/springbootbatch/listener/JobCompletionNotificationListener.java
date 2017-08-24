package org.dante.springboot.springbootbatch.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.dante.springboot.springbootbatch.po.PersonPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * 任务执行监听器
 * 
 * @author dante
 *
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("JOB 开始执行......");
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("JOB 完成，开始验证 job 执行的正确性！");

			List<PersonPO> results = jdbcTemplate.query("SELECT first_name, last_name FROM people",
					new RowMapper<PersonPO>() {
						@Override
						public PersonPO mapRow(ResultSet rs, int row) throws SQLException {
							return new PersonPO(rs.getString(1), rs.getString(2));
						}
					});

			for (PersonPO person : results) {
				log.info("在数据库中获取Person[ {} ]", person);
			}

		}
	}
}
