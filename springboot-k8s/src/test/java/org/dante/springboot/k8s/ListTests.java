package org.dante.springboot.k8s;

import org.junit.Test;

import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.extensions.DeploymentList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListTests extends SpringbootK8SApplicationTests {

	@Test
	public void listNode() {
		client.nodes().list().getItems().forEach(n -> log.info("Node: {}", n));
	}
	
	@Test
	public void listNamespace() {
		NamespaceList myNs = client.namespaces().list();
		myNs.getItems().forEach(n -> log.info(n.getMetadata().getName()));
	}
	
	@Test
	public void listDeployment() {
		DeploymentList deployList = client.extensions().deployments().inNamespace(KUBE_NAMESPACE).list();
		log.info("DeploymentList: {}", deployList);
	}
	
	@Test
	public void listPod() {
		PodList pods = client.pods().inNamespace(KUBE_NAMESPACE).list();
		pods.getItems().forEach(p -> log.info("Pod: {}", p));
	}
	
	@Test
	public void listService() {
		client.services().inNamespace(ISTIO_NAMESPACE).list().getItems().forEach(svc -> log.info("Service: {}", svc));
	}
	
	@Test
	public void listEndpoint() {
		client.endpoints().inNamespace(ISTIO_NAMESPACE).list().getItems().forEach(ep -> log.info("Endpoint: {}", ep));
	}
	
	@Test
	public void listIngress() {
		client.extensions().ingresses().inNamespace(ISTIO_NAMESPACE).list().getItems().forEach(ing -> log.info("Ingress: {}", ing));
	}
	
}
