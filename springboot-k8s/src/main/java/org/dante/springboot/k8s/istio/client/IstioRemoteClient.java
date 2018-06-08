package org.dante.springboot.k8s.istio.client;

import java.util.Map;

import org.dante.springboot.k8s.istio.client.fallback.IstioRemoteFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="k8s-istio-api", url="${k8s.serverUrl}/apis/config.istio.io", fallback=IstioRemoteFallback.class)
public interface IstioRemoteClient {

	@RequestMapping(value="/v1alpha2/namespaces/{ns}/routerules")
	public String listRouterules(@PathVariable("ns") String ns);
	
	@RequestMapping(value="/v1alpha2/namespaces/{ns}/routerules/{routerule}")
	public String getRouterule(@PathVariable("ns") String ns, @PathVariable("routerule") String routerule);
	
	@RequestMapping(value="/v1alpha2/namespaces/{ns}/routerules", method=RequestMethod.DELETE)
	public String deleteRouterules(@PathVariable("ns") String ns);
	
	@RequestMapping(value="/v1alpha2/namespaces/{ns}/routerules/{routerule}", method=RequestMethod.DELETE)
	public String deleteRouterule(@PathVariable("ns") String ns, @PathVariable("routerule") String routerule);
	
	@RequestMapping(value="/v1alpha2/namespaces/{ns}/routerules/create", method=RequestMethod.POST)
	public String createRouterule(@PathVariable("ns") String ns, Map<String, Map<String, Object>> param);
	
	@RequestMapping(value="/v1alpha2/namespaces/{ns}/routerules/{routerule}", method=RequestMethod.PUT)
	public String replaceRouterule(@PathVariable("ns") String ns, @PathVariable("routerule") String routerule, Map<String, Map<String, Object>> param);
}
