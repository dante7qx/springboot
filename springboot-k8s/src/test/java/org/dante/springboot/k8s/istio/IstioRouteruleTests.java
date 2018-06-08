package org.dante.springboot.k8s.istio;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.dante.springboot.k8s.SpringbootK8SApplicationTests;
import org.dante.springboot.k8s.istio.client.IstioRemoteClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import me.snowdrop.istio.api.model.IstioBaseSpecBuilder;
import me.snowdrop.istio.api.model.IstioResource;
import me.snowdrop.istio.api.model.IstioResourceBuilder;
import me.snowdrop.istio.api.model.v1.routing.DestinationWeightBuilder;
import me.snowdrop.istio.api.model.v1.routing.RouteRule;
import me.snowdrop.istio.api.model.v1.routing.StringMatch;

@Slf4j
public class IstioRouteruleTests extends SpringbootK8SApplicationTests {

	@Autowired
	private IstioRemoteClient istioRemoteClient; 
	@Autowired
	private RestTemplate restTemplate;
	
	@Test
	public void ribbonTest() {
		String routerules = restTemplate.getForObject("http://localhost:8001/apis/config.istio.io/v1alpha2/namespaces/default/routerules", String.class);
		log.info("routerules: {}.", routerules);
	}
	
	@Test
	public void listRouterules() {
		try {
			log.info("Routerules: {}.", istioRemoteClient.listRouterules(DANTE_NAMESPACE));
		} catch (Exception e) {
			log.error("listRouterules error.", e);
		}
	}
	
	@Test
	public void getRouterule() {
		String routeruleJson = istioRemoteClient.getRouterule(DANTE_NAMESPACE, "reviews-test-v2");
		log.info("RouteruleJson: {}", routeruleJson);
		try {
			IstioResource routeRule = jsonMapper.readValue(routeruleJson, IstioResource.class);
			log.info("IstioResource {}", routeRule.getMetadata().getResourceVersion());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void createOrReplaceRouterule() throws Exception {
		String routeruleJson = istioRemoteClient.getRouterule(DANTE_NAMESPACE, "reviews-test-v2");
		if(StringUtils.isNotEmpty(routeruleJson)) {
			IstioResource routeRule = jsonMapper.readValue(routeruleJson, IstioResource.class);
			RouteRule spec = (RouteRule) routeRule.getSpec();
			spec.getMatch().getRequest().getHeaders().get("cookie").setAdditionalProperty("regex", "^(.*?;)?(user=dante)(;.*)?$");
			spec.setRoute(Lists.newArrayList(
					new DestinationWeightBuilder()
		 			.addToLabels("version", "v3")
		 		.build()
			));
			istioRemoteClient.replaceRouterule(DANTE_NAMESPACE, "reviews-test-v2", buildIstioParam(routeRule));
		} else {
	        istioRemoteClient.createRouterule(DANTE_NAMESPACE, buildIstioParam(buildNewIstioRouterRule()));
		}
	}
	
	@Test
	public void deleteRouterules() {
		try {
			String result = istioRemoteClient.deleteRouterules(DANTE_NAMESPACE);
			log.info("delete result: {}.", result);
		} catch (Exception e) {
			log.error("deleteRouterules error.", e);
		}
	}
	
	@Test
	public void deleteRouterule() {
		try {
			String result = istioRemoteClient.deleteRouterule(DANTE_NAMESPACE, "reviews-default");
			log.info("delete result: {}.", result);
		} catch (Exception e) {
			log.error("deleteRouterule error.", e);
		}
	}
	
	
	private IstioResource buildNewIstioRouterRule() {
		StringMatch headerMatch = new StringMatch();
		headerMatch.setAdditionalProperty("regex", "^(.*?;)?(user=jason)(;.*)?$");
		final IstioResource routeRule = new IstioResourceBuilder()
				.withApiVersion("config.istio.io/v1alpha2")
				.withKind("RouteRule")
				.withNewMetadata()
				 	.withName("reviews-test-v2")
				.endMetadata()
				.withNewRouteRuleSpec()
				 	.withNewDestination()
				 		.withName("reviews")	// Service metadata Name
				 	.endDestination()
				 	.withPrecedence(2)
				 	.withNewMatch()
				 		.withNewRequest()
				 			.addToHeaders("cookie", headerMatch)
				 		.endRequest()
				 	.endMatch()
				 	.withRoute(
				 		new DestinationWeightBuilder()
				 			.addToLabels("version", "v2")
				 		.build()
		 			)
				.endRouteRuleSpec()
				.build();
		return routeRule;
	}
	
	
	private IstioResource buildNetworkGateway() {
		final IstioResource resource = new IstioResourceBuilder()
				.withApiVersion("networking.istio.io/v1alpha3")
				.withKind("Gateway")
				.withNewMetadata()
					.withName("bookinfo-gateway")
				.endMetadata()
				
				
				
				.build();
		
		return resource;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, Object>> buildIstioParam(IstioResource routeRule) throws JsonProcessingException {
		final String output = mapper.writeValueAsString(routeRule);
		log.info("Istio Routerule: {}", output);
        Yaml parser = new Yaml();
        return parser.loadAs(output, Map.class);
	}
	
}
