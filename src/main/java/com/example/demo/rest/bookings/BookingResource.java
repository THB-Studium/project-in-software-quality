package com.example.demo.rest.bookings;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Booking;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.BookingService;

@RestController
@RequestMapping(ApiConstants.BOOKING_ITEM)
public class BookingResource {
	private static final Logger log = LoggerFactory.getLogger(BookingRootResource.class);
	
	@Autowired
	private BookingService bookingService;
	
	
	
	/**
	 * TO GET ONE BOOKING BY HIS ID: 
	 * path: "/api/bookings/{bookingId}"
	 * 
	 * @param bookingId
	 * @return
	 */
        @Produces(MediaType.APPLICATION_JSON)
        @RequestMapping(method = RequestMethod.GET)
	public Booking getOneBooking(@PathParam("bookingId") UUID bookingId) {
		Booking bookingFound = bookingService.getOne(bookingId);
		log.info(
				String.format("The Booking with the id=%s has been found!"), bookingId.toString());
		return bookingFound;
	}
	
	/**
	 * TO UPDATE A BOOKING
	 * path: "/api/bookings/{bookingId}"
	 * 
	 * @param bookingId
	 * @param update
	 */
	@Consumes(MediaType.APPLICATION_JSON)
        @RequestMapping(method = RequestMethod.PUT)
	public void updateBooking(@PathParam("bookingId") UUID bookingId, Booking update) {
		bookingService.update(bookingId, update);
		log.info(String.format("The Booking with the id=%s has been successfully updated!"), bookingId.toString());
	}
	
	
	/**
	 * TO DELETE A BOOKING
	 * 
	 * @param bookingId
	 */
        @RequestMapping(method = RequestMethod.DELETE)
	public void deleteBooking(@PathParam("bookingId") UUID bookingId) {
		bookingService.delete(bookingId);
		log.info(String.format("The Booking with the id=%s has been successfully updated!"), bookingId.toString());
	}

}
