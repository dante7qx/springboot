package org.dante.springboot.k8s;

import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Test;

import com.google.common.collect.Maps;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.HTTPGetAction;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.Probe;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.extensions.DeploymentSpec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeploymentTest extends SpringbootK8SApplicationTests {

	@Test
	public void createDeployment() {
		DeploymentSpec spec = new DeploymentSpec();
		spec.setReplicas(3);
		
		Map<String, String> labels = Maps.newHashMap();
		labels.put("app", "app-health-check");
		
		LabelSelector selector = new LabelSelector();
		selector.setMatchLabels(labels);
		
		PodTemplateSpec podTemplate = new PodTemplateSpec();
		ObjectMeta podMeta = new ObjectMeta();
		podMeta.setLabels(labels);
		podTemplate.setMetadata(podMeta);
		
		Container container1 = new Container();
		container1.setName("api-health-check");
		container1.setImage("dante/springboot-docker");
		container1.setImagePullPolicy("Never");
		ContainerPort port1 = new ContainerPort();
		port1.setContainerPort(8080);
		container1.setPorts(Lists.newArrayList(port1));
		
		Probe probe = new Probe();
		HTTPGetAction httpGet = new HTTPGetAction();
		httpGet.setPath("/healthz");
		httpGet.setPort(new IntOrString(8080));
		probe.setHttpGet(httpGet);
		probe.setInitialDelaySeconds(120);
		probe.setTimeoutSeconds(5);
		container1.setLivenessProbe(probe);
		
		PodSpec podSpec = new PodSpec();
		podSpec.setContainers(Lists.newArrayList(container1));
		podTemplate.setSpec(podSpec);
		
		
		spec.setSelector(selector);
		spec.setTemplate(podTemplate);
		
		Deployment deployment = new DeploymentBuilder()
				.withApiVersion("extensions/v1beta1")
				.withKind("Deployment")
				.withNewMetadata()
					.withName("api-deployment")
					.withNamespace(DANTE_NAMESPACE)
				.endMetadata()
				.withSpec(spec)
				.build();
		Deployment deployResult = client.extensions().deployments().inNamespace(DANTE_NAMESPACE).create(deployment);
		log.info("createDeployment: {}.", deployResult);
	}
	
	@Test
	public void scale() {
		client.extensions().deployments().inNamespace(DANTE_NAMESPACE).withName("api-deployment").scale(0);
		log.info("创建 replica sets: {}", client.extensions().replicaSets().inNamespace(DANTE_NAMESPACE).list().getItems());
	}
	
	@Test
	public void deleteDeployment() {
		client.extensions().deployments().inNamespace(DANTE_NAMESPACE).withName("api-deployment").delete();
	}
}
