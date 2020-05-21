package org.dante.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域访问方式一
 * 
 * @author dante
 *
 */
@Configuration
public class CorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.addExposedHeader("Content-Type");
		config.addExposedHeader("X-Requested-With");
		config.addExposedHeader("accept");
		config.addExposedHeader("Origin");
		config.addExposedHeader("Access-Control-Request-Method");
		config.addExposedHeader("Access-Control-Request-Headers");

		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);

		return new CorsFilter(configSource);
	}
}