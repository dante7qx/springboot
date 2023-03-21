package org.dante.springboot.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.KeyGenerator;
import javax.sql.DataSource;

import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.dante.springboot.algorithm.ModuloShardingDatabaseAlgorithm;
import org.dante.springboot.algorithm.ModuloShardingTableAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;


@Configuration
public class SpiritShardingRuleConfig {

	@ConfigurationProperties(prefix = "spring.datasource.ds-0.hikari")
	@Bean(name = "ds_0")
	public DataSource dataSource0() {
		return new HikariDataSource();
	}

	@ConfigurationProperties(prefix = "spring.datasource.ds-1.hikari")
	@Bean(name = "ds_1")
	public DataSource dataSource1() {
		return new HikariDataSource();
	}

	@Primary
	@Bean(name = "shardingDataSource")
	DataSource getShardingDataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
		shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
		
		// 分库策略：按user_id模2分库，ds_0、ds_1
		shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
				new StandardShardingStrategyConfiguration("user_id", new ModuloShardingDatabaseAlgorithm()));
		
		// 分表策略：按order_id模2分表，实现PreciseShardingAlgorithm
		shardingRuleConfig.setDefaultTableShardingStrategyConfig(
				new StandardShardingStrategyConfiguration("order_id", new ModuloShardingTableAlgorithm()));

		return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig,
				new HashMap<String, Object>(), new Properties());
	}

	/**
	 * 配置Order表规则
	 * 
	 * @return
	 */
	private static TableRuleConfiguration getOrderTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_order");
		result.setActualDataNodes("ds${0..1}.t_order_${0..1}");
		result.setKeyGeneratorColumnName("order_id");
		return result;
	}

	private static TableRuleConfiguration getOrderItemTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_order_item");
		result.setActualDataNodes("ds${0..1}.t_order_item_${0..1}");
		result.setKeyGeneratorColumnName("order_item_id");
		return result;
	}

	Map<String, DataSource> createDataSourceMap() {
		Map<String, DataSource> result = new HashMap<>();
		result.put("ds0", dataSource0());
		result.put("ds1", dataSource1());
		return result;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new DefaultKeyGenerator();
	}
}
