package org.dante.springboot.api;

public interface CabBookingService {
	String bookRide(String pickUpLocation) throws BookingException;
}
