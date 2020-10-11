package org.dante.springboot.mongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableReactiveMongoRepositories
@Configuration
public class SpiritMongoConfig extends AbstractReactiveMongoConfiguration {
 
    @Override
    protected String getDatabaseName() {
        return "reactive";
    }

	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create();
	}
}
