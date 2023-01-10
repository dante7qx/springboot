package org.dante.springboot;

import org.dante.springboot.api.CabBookingService;
import org.dante.springboot.service.CabBookingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

@SuppressWarnings("deprecation")
@SpringBootApplication
public class SpringbootRMIServerApplication {

	@Bean 
	CabBookingService bookingService() {
        return new CabBookingServiceImpl();
    }

	@Bean 
	RemoteExporter exporter(CabBookingService implementation) {
        // Expose a service via RMI. Remote obect URL is: rmi://<HOST>:<PORT>/<SERVICE_NAME> 1099 is the default port
        Class<CabBookingService> serviceInterface = CabBookingService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootRMIServerApplication.class, args);
	}
}
