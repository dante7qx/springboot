package org.dante.springboot.ws.interceptor;

import javax.xml.transform.Source;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WsInterceptor extends EndpointInterceptorAdapter {
	
	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		WebServiceMessage request = messageContext.getRequest();
		Source source = request.getPayloadSource();
		log.info("===> Source: " + source.toString());
		return true;
	}

}
