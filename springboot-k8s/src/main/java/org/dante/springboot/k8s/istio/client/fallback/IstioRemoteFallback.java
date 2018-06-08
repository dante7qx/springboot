package org.dante.springboot.k8s.istio.client.fallback;

import java.util.Map;

import org.dante.springboot.k8s.istio.client.IstioRemoteClient;
import org.springframework.stereotype.Component;

@Component
public class IstioRemoteFallback implements IstioRemoteClient {

	@Override
	public String listRouterules(String ns) {
		return "-1";
	}
	
	@Override
	public String deleteRouterules(String ns) {
		return "-1";
	}

	@Override
	public String deleteRouterule(String ns, String routerule) {
		return "-1";
	}

	@Override
	public String createRouterule(String ns, Map<String, Map<String, Object>> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String replaceRouterule(String ns, String routerule, Map<String, Map<String, Object>> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRouterule(String ns, String routerule) {
		// TODO Auto-generated method stub
		return null;
	}


}
