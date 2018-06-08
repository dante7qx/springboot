package org.dante.springboot.k8s.factory;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class K8sFactory {
	
	public static final KubernetesClient instance(String master) { 
		KubernetesClient client = new DefaultKubernetesClient(master);
		return client;
	}
	
}