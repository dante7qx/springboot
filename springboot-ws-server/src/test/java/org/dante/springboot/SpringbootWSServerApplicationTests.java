package org.dante.springboot;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource; 

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootWSServerApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	private MockWebServiceClient mockClient;

	@Before
	public void createClient() {
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}
	
	@Test
	public void countrysEndpoint() throws Exception {
		Source requestPayload = new StringSource("<getCountryRequest xmlns='http://org.dante.springboot/ws'>"
				+ "<name>Spain</name>" + "</getCountryRequest>");
		Source responsePayload = new StringSource("<getCountryResponse xmlns='http://org.dante.springboot/ws'>"
				+ "<country>"
					+"<name>Spain</name>"
					+"<population>46704314</population>"
					+"<capital>Madrid</capital>"
					+"<currency>EUR</currency>"
				+"</country>"
				+ "</getCountryResponse>");

		mockClient.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
	}
}
