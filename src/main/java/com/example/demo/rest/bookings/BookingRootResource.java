package com.example.demo.rest.bookings;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Booking;
import com.example.demo.model.SeminarVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.BookingService;
import com.example.demo.service.exception.NoSeminarException;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@RestController
@RequestMapping(ApiConstants.BOOKING_COLLECTION)
public class BookingRootResource {
    private static final Logger log = LoggerFactory.getLogger(BookingRootResource.class);

    @Autowired
    private BookingService bookingService;

    
    
    /**
     * TO GET ALL BOOKINGS: path: "/api/bookings"
     * 
     * @return
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Booking> getAllBookings() {
        Set<Booking> bookings = bookingService.listAll();
        log.info("Bookings have been successfuly listed!");
        return bookings;
    }

    /**
     * TO ADD/CREATE A NEW BOOKING: path: "/api/bookings"
     * 
     * @param newBooking
     * @return
     */
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Booking addBoking(@RequestBody Booking newBooking) {
        Booking createdBooking = bookingService.create(newBooking);
        log.info("Booking successfully created!");
        return createdBooking;
    }
    
    // about
    // Seminar:.............................................................

    /**
     * TO START A SEMINAR
     * path: "/api/bookings/seminar/start"
     * 
     * @param seminarId
     * @return
     * @throws NoSeminarException
     */
    @RequestMapping(
            path = "/seminar/start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SeminarVO startNewSeminar(@RequestBody SeminarVO newSeminar) throws NoSeminarException {
        SeminarVO startedSeminar = bookingService.startNewSeminar(newSeminar);
        log.info(String.format("A new Seminar with the id=%s has been successfully started!", newSeminar.getId()));
        return startedSeminar;

    }

}
