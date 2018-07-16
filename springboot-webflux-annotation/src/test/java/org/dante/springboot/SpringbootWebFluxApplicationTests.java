package org.dante.springboot;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootWebFluxApplicationTests {

	@Test
	public void webClientTest() throws InterruptedException {
		WebClient webClient = WebClient.create("http://localhost:8080");   // 1
        Mono<String> resp = webClient
                .get().uri("/") // 2
                .retrieve() // 3
                .bodyToMono(String.class);  // 4
        resp.subscribe(System.out::println);    // 5
        TimeUnit.SECONDS.sleep(1);  // 6
	}

}
