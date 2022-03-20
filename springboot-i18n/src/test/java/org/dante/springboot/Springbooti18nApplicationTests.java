package org.dante.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class Springbooti18nApplicationTests {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void msgTest() throws Exception {
		this.mvc.perform(get("/msg").header("Accept-Language", "ja-jp"))
				.andExpect(status().isOk())
				.andDo(new ResultHandler() {
					@Override
					public void handle(MvcResult result) throws Exception {
						log.info(result.getResponse().getContentAsString());
					}
				});
	}

}
