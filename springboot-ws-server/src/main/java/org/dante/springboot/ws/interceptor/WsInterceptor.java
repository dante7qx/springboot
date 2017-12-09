package org.dante.springboot.ws.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Source;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WsInterceptor extends EndpointInterceptorAdapter {
	
	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		WebServiceMessage wsRequest = messageContext.getRequest();
		Source source = wsRequest.getPayloadSource();
		
		TransportContext context = TransportContextHolder.getTransportContext();
		HttpServletConnection connection = (HttpServletConnection )context.getConnection();
		HttpServletRequest request = connection.getHttpServletRequest();
		String ipAddress = request.getRemoteAddr();
		log.info("IP {} ===> Source {}", ipAddress, source.toString());
		return true;
	}

}
