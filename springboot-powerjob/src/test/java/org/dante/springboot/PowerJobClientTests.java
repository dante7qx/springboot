package org.dante.springboot;

import java.util.List;

import org.junit.jupiter.api.Test;

import cn.hutool.core.lang.Console;
import tech.powerjob.client.PowerJobClient;
import tech.powerjob.common.response.JobInfoDTO;
import tech.powerjob.common.response.ResultDTO;

public class PowerJobClientTests {

	@Test
	void testClient() {
		// 初始化 client，需要server地址和应用名称作为参数
		PowerJobClient client = new PowerJobClient("127.0.0.1:7700", "springboot-powerjob", "iamdante");
		ResultDTO<List<JobInfoDTO>> result = client.fetchAllJob();
		Console.log("Result -> {}", result);
	}
	
}
