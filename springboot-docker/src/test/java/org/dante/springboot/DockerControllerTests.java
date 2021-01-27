package org.dante.springboot;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.dante.springboot.docker.PropertiesUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DockerControllerTests extends SpringbootDockerApplicationTests {
	
	@Test
	public void docker() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/docker").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string(containsString("Docker")));
	}
	
	@Test
	public void testPropertiesUtil() {
		System.out.println("==========> " + PropertiesUtils.getString("hello.msg"));
	}
}
