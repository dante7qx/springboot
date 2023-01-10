package org.dante.springboot;

import org.dante.springboot.api.BookingException;
import org.dante.springboot.api.CabBookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@SpringBootApplication
@SuppressWarnings("deprecation")
public class SpringbootRMIClientApplication {

	@Bean
	RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/CabBookingService");
        rmiProxyFactory.setServiceInterface(CabBookingService.class);
        return rmiProxyFactory;
    }
	
	public static void main(String[] args) throws BookingException {
		CabBookingService service = SpringApplication.run(SpringbootRMIClientApplication.class, args).getBean(CabBookingService.class);
        String bookingOutcome = service.bookRide("天水市秦州区羲皇大道中路灵鸽幼儿园东侧约110米 ");
        System.out.println(bookingOutcome);
	}
}
