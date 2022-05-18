package org.dante.springboot.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class SpiritMongoConfig {

	@Bean
	MongoDatabaseFactory mongoDbFactory() {
		return new SimpleMongoClientDatabaseFactory("mongodb://127.0.0.1:27017/springboot");
	}
	
	@Bean
	MongoTemplate mongoTemplate(@Autowired MongoDatabaseFactory mongoDbFactory) {
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory),
				new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return new MongoTemplate(mongoDbFactory, converter);

	}
}
