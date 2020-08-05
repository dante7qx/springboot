package org.dante.springboot;

import org.dante.springboot.prop.QrCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(QrCodeProperties.class)
public class SpringbootQrCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootQrCodeApplication.class, args);
	}
}
