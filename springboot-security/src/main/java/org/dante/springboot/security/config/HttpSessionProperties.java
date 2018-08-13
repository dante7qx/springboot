package org.dante.springboot.security.config;

public interface HttpSessionProperties {
	
	public final static String HTTP_REDIS_NAMESPACE = "dante-session";
	public final static int HTTP_INACTIVE_INTERVAL_SECONDS = 60;
	
}
