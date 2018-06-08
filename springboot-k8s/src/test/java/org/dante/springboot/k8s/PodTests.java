package org.dante.springboot.k8s;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.ContainerPortBuilder;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.VolumeMountBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PodTests extends SpringbootK8SApplicationTests {

	@Test
	public void createPod() {
		Map<String, String> labels = Maps.newHashMap();
		labels.put("pod-io", "api-create-pod");
		Map<String, String> annotations = Maps.newHashMap();
		annotations.put("sidecar", "istio-proxy");
		Pod pod = new PodBuilder()
					.withApiVersion("v1")
					.withKind("Pod")
					.withNewMetadata()
						.withName("api-pod-test")
						.withNamespace(DANTE_NAMESPACE)
						.withLabels(labels)
						.withAnnotations(annotations)
					.endMetadata()
					.withNewSpec()
						.addToContainers(
								new ContainerBuilder()
									.withName("api-container")
									.withImage("dante/springboot-docker")
									/**
									 * Always: 每次都重新拉取镜像
									 * IfNotPresent: 本地有则使用本地的镜像，否则去拉取镜像
									 * Never: 仅使用本地镜像
									 */
									.withImagePullPolicy("IfNotPresent") 
									/**
									 * 容器的存储卷
									 */
									.withVolumeMounts(
										new VolumeMountBuilder()
											.withName("api-volume")
											.withMountPath("/logs")
//											.withReadOnly(true)
										.build()
									)
									/**
									 * 端口配置
									 */
									.withPorts(
										new ContainerPortBuilder()
											.withContainerPort(8080)
											.withProtocol("TCP")
										.build()
									)
									/**
									 * 环境变量
									 */
									.withEnv(
										new EnvVar("spring_profiles_active", "dev", null),
										new EnvVar("hello_msg", "K8S，你好", null)
									)
									/**
									 * 配额管理
									 */
									.withNewResources()
										.addToRequests("cpu", new Quantity("500m"))
										.addToRequests("memory", new Quantity("1024Mi"))
										.addToLimits("cpu", new Quantity("1000m"))
										.addToLimits("memory", new Quantity("2048Mi"))
									.endResources()
								.build()
							)
						.withVolumes(
							new VolumeBuilder()
								.withName("api-volume")
								.withNewHostPath("/Users/dante/Desktop/xxxx")
							.build()
						)
						/**
						 * 重启策略
						 * Always：Pod一旦终止运行，k8s就重启它
						 * OnFailure：Pod 非零终止码，才会重启
						 * Never: Pod将终止码报告给 Master, k8s不会重启
						 */
						.withRestartPolicy("Always")
					.endSpec()
					.build();
		Pod newPod = client.pods().inNamespace(DANTE_NAMESPACE).createOrReplace(pod);
		log.info("Create Pod {}.", newPod);
	}
	
	@Test
	public void deletePod() {
		client.pods().inNamespace(DANTE_NAMESPACE).withName("api-pod-test").delete();
//		client.pods().inNamespace(DANTE_NAMESPACE).withLabel("pod-io", "api-create-pod").delete();
	}
	
	@Test
	public void logPod() throws InterruptedException {
		client.pods().inNamespace(DANTE_NAMESPACE).withName("api-pod-test")
			.tailingLines(10).watchLog(System.out);
		Thread.sleep(120000);
	}
}
