package org.dante.springboot;

import org.junit.jupiter.api.Test;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class HelloWorldTest {
	
	@Test
	public void helloWorld() {
		// 1. 定义Schema, 一般会定义在一个schema文件中
		String schema = "type Query{hello: String}";
		// 2. 解析Schema
		SchemaParser schemaParser = new SchemaParser();
		TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);
		// 为Schema 中hello方法绑定获取数据的方法
		RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
		        // 这里绑定的是最简单的静态数据数据获取器, 正常使用时,获取数据的方法返回一个DataFetcher实现即可
		        .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
		        .build();
		// 将TypeDefinitionRegistry与RuntimeWiring结合起来生成GraphQLSchema
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
		// 实例化GraphQL, GraphQL为执行GraphQL语言的入口
		GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
		// 执行查询
		ExecutionResult executionResult = graphQL.execute("{hello}");
		// 打印执行结果
		System.out.println(executionResult.getData().toString());
	}

}
