package org.dante.springboot.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HelloRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:/Users/dante/Desktop/a")
        	.to("file:/Users/dante/Desktop/b");
	}

}
