package org.dante.springboot;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DockerControllerTests extends SpringbootDockerApplicationTests {
	@Test
	public void docker() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/docker").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string(containsString("Docker")));
	}
}
