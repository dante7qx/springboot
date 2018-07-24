package org.dante.springboot.webclient;

import org.dante.springboot.vo.GithubUserVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class GithubClient {
	
	WebClient client = WebClient.create("https://api.github.com");
	
	/**
	 * 获取 Github 用户详情
	 * 
	 * @param userId
	 * @return
	 */
	public Mono<GithubUserVO> getUser(String userId) {
		Mono<GithubUserVO> githubUserVO = client.get()
			.uri("/users/{uid}", userId)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(GithubUserVO.class).log();
		return githubUserVO;
	}

}
