package org.dante.springboot.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.dante.springboot.handler.TimeHandler;
import org.dante.springboot.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
	
	@Autowired
	private TimeHandler timeHandler;
	@Autowired
	private UserHandler userHandler;
	
	@Bean
	public RouterFunction<ServerResponse> timeRouter() {
		 return route(GET("/date"), timeHandler::getDate)
				.andRoute(GET("/time"), timeHandler::getTime)
				.andRoute(GET("/pertime"), timeHandler::sendTimePerSec);
	}
	
	@Bean
	public RouterFunction<ServerResponse> userRouter() {
		 return route(GET("/user/{name}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
				.andRoute(GET("/users"), userHandler::getUsers)
				.andRoute(POST("/users"), userHandler::getUsers)
				.andRoute(GET("/github/{uid}"), userHandler::getGithubUser);
	}

}
