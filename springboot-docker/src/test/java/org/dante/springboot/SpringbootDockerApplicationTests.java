package org.dante.springboot;

import org.junit.Before;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringbootDockerApplicationTests {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	protected MockMvc mockMvc;
	
	@Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void testKong() {
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Host", "127.0.0.1");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);		
		ResponseEntity<String> resp = restTemplate.exchange("http://127.0.0.1:8000/msg", HttpMethod.GET, requestEntity, String.class);
		String body = resp.getBody();
		System.out.println("=============> " + body);
	}
}
