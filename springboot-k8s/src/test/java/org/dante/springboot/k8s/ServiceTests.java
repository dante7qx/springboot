package org.dante.springboot.k8s;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

import io.fabric8.kubernetes.api.model.DoneableService;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServiceStatus;
import io.fabric8.kubernetes.client.dsl.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceTests extends SpringbootK8SApplicationTests {

	@Test
	public void createSvc() {
		Map<String, String> labels = Maps.newHashMap();
		labels.put("app", "app-health-check");
		Service service = new ServiceBuilder()
				.withApiVersion("v1")
				.withKind("Service")
				.withNewMetadata()
					.withName("api-health-check")
					.withNamespace(DANTE_NAMESPACE)
					.withLabels(labels)
				.endMetadata()
				.withNewSpec()
					.withType("NodePort")
					.addNewPort()
					.withPort(8080)
					.withNodePort(30003)
					.endPort()
					.withSessionAffinity("ClientIP")
					.addToSelector("app", "app-health-check")
					.addToSelector("pod-io", "api-create-pod")
				.endSpec()
				.build();
		Service svc = client.services().inNamespace(DANTE_NAMESPACE).create(service);
		ServiceStatus status = svc.getStatus();
		log.info("Service: {}", svc);
		log.info("Status: {}", status);
	}
	
	@Test
	public void deleteSvc() {
		Resource<Service, DoneableService> resource = client.services().inNamespace(DANTE_NAMESPACE).withName("api-health-check");
		resource.delete();
	}
}
