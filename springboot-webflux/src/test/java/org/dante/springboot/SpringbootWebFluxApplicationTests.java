package org.dante.springboot;

import java.sql.Date;
import java.time.Instant;
import java.util.Collections;

import org.dante.springboot.po.AddressPO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * MethodSorters.NAME_ASCENDING: 按方法名称的进行排序，由于是按字符的字典顺序，所以以这种方式指定执行顺序会始终保持一致
 * 
 * @author dante
 *
 */
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootWebFluxApplicationTests {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void test1PersistAddress() {
		AddressPO address = new AddressPO(null, "2001", "风之谷-千寻", Date.from(Instant.now()));
		webTestClient.post().uri("/address")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(address), AddressPO.class)
			.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.name").isEqualTo("风之谷-千寻");
	}
	
	@Test
	public void test2QueryAllAddress() {
		webTestClient.get().uri("/address")
			.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.expectBodyList(AddressPO.class)
				.hasSize(5)
				.consumeWith(addr -> log.info("Address -> {}", addr));
	}
	
	@Test
	public void test3QueryAddressById() {
		webTestClient.get()
			.uri("/address/{code}", Collections.singletonMap("code", "1003"))
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.name").isEqualTo("北辰世纪中心A座");
	}
	
	@Test
	public void test4DeleteAddress() {
		webTestClient.delete()
			.uri("/address/{code}", "2001")
			.exchange()
			.expectStatus().isOk();
	}
	
	@Test
	public void test4Kong() {
		webTestClient.get()
			.uri("http://10.71.225.139:8000/msg")
			.header("Host", "10.71.225.139")
			.exchange()
			.returnResult(String.class)
            .getResponseBody()
            .subscribe(r -> log.info("================> {}", r));
	}
	
}
