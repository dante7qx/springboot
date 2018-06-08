package org.dante.springboot.k8s;

import org.dante.springboot.k8s.factory.K8sFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * 参考：https://github.com/fabric8io/kubernetes-client/tree/master/kubernetes-examples
 * 
 * @author dante
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootK8SApplicationTests {

	protected static final String KUBE_NAMESPACE = "kube-system";
	protected static final String ISTIO_NAMESPACE = "istio-system";
	protected static final String DANTE_NAMESPACE = "dante";
		
	protected final YAMLMapper mapper = new YAMLMapper();
	protected final ObjectMapper jsonMapper = new ObjectMapper();
	
	protected KubernetesClient client;
	
	@Before
	public void init() {
		client = K8sFactory.instance("https://localhost:6443");
	}
	
	@After
	public void close() {
		client.close();
	}
}