package org.dante.springboot.service;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;

import graphql.GraphQL;
import graphql.com.google.common.base.Charsets;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Component
public class GraphQLProvider {

	@Autowired
	private GraphQLDataFetchers graphQLDataFetchers;

	private GraphQL graphQL;

	@PostConstruct
	public void init() throws IOException {
		URL url = Resources.getResource("schema.graphqls");
		String sdl = Resources.toString(url, Charsets.UTF_8);
		GraphQLSchema graphQLSchema = buildSchema(sdl);
		this.graphQL = GraphQL.newGraphQL(graphQLSchema)
			.build();
	}

	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
			// 仅仅是体验Mutation这个功能,返回一个字符串
			.type("Mutation", builder -> builder.dataFetcher("hello", new StaticDataFetcher("Mutation hello world")))
			// 返回字符串
			.type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("Query hello world")))
			// 通过id查询book
			.type(newTypeWiring("Query").dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
			// 查询所有的book
			.type(newTypeWiring("Query").dataFetcher("books", graphQLDataFetchers.getAllBooks()))
			// 查询book中的author信息
			.type(newTypeWiring("Book").dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
			.build();
	}

	// 执行GraphQL语言的入口
	@Bean
	public GraphQL graphQL() {
		return graphQL;
	}
}
