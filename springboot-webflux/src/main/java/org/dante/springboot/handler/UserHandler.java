package org.dante.springboot.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.dante.springboot.vo.GithubUserVO;
import org.dante.springboot.vo.UserVO;
import org.dante.springboot.webclient.GithubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserHandler {
	
	@Autowired
	private GithubClient githubClient;

	/**
	 * 根据 name 获取用户
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getUser(ServerRequest request) {
		String name = request.pathVariable("name");
		log.info("PathVariable name = {}", name);
		return ok().contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(new UserVO("但丁", 33, "ch.sun@haihangyun.com")), UserVO.class);
	}

	/**
	 * 获取 User 列表
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getUsers(ServerRequest request) {
		String methodName = request.methodName();
		Flux<UserVO> userParameter = request.bodyToFlux(UserVO.class);
		log.info("Request method {}", methodName);
		userParameter.subscribe(u -> log.info("UserParameter {}", u));
		return ok().contentType(MediaType.APPLICATION_JSON).body(
				Flux.just(new UserVO("但丁", 33, "ch.sun@haihangyun.com"), new UserVO("Snake", 45, "snake@163.com")),
				UserVO.class);
	}
	
	/**
	 * 获取 Github 用户详情
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getGithubUser(ServerRequest request) {
		String uid = request.pathVariable("uid");
		Mono<GithubUserVO> user = githubClient.getUser(uid);
		return ok().contentType(MediaType.APPLICATION_JSON).body(user, GithubUserVO.class);
	}
}
