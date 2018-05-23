package org.dante.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.Config;

/**
 * 参考：https://github.com/kubernetes-client/java
 * 
 * @author dante
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootK8SApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootK8SApplicationTests.class);
	
	@Test
	public void listPods() throws Exception {
		ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        for (V1Pod item : list.getItems()) {
            LOGGER.info("Pod name --> {}", item.getMetadata().getName());
        }
	}
}