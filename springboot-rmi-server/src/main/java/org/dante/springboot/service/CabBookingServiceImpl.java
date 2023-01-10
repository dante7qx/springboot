package org.dante.springboot.service;

import static java.lang.Math.random;
import static java.util.UUID.randomUUID;

import org.dante.springboot.api.BookingException;
import org.dante.springboot.api.CabBookingService;

public class CabBookingServiceImpl implements CabBookingService {

	@Override
	public String bookRide(String pickUpLocation) throws BookingException {
		if (random() < 0.3) {
			throw new BookingException("出租车无法到达【" + pickUpLocation + "】！");
		}
		return "出租车[" + randomUUID().toString() + "]正在赶来"; 
	}

}
