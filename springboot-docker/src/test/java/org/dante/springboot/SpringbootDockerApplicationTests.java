package org.dante.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringbootDockerApplicationTests {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Test
	public void testKong() {
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Host", "10.71.225.139");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);		
		ResponseEntity<String> resp = restTemplate.exchange("http://10.71.225.139:8000/msg", HttpMethod.GET, requestEntity, String.class);
		String body = resp.getBody();
		System.out.println("=============> " + body);
	}
}
