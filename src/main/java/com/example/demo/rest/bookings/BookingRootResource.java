package com.example.demo.rest.bookings;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.TransferOpject.IdTO;
import com.example.demo.model.Booking;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.BookingService;

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
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.GET)
    public Set<Booking> getAllBookings() {
        Set<Booking> bookings = bookingService.listAll();
        log.info("Bookings have been successfuly listed!");
        return bookings;
    }

    /**
     * TO ADD/CREATE A NEW BOOKING: path: "/api/bookings"
     * 
     * @param uriInfo
     * @param newBooking
     * @return
     */
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public Response addBoking(@Context UriInfo uriInfo, Booking newBooking) {
        Booking createdBooking = bookingService.create(newBooking);

        // build of the response:
        URI uri = uriInfo.getRequestUriBuilder()
                .path(createdBooking.getId().toString()).build();

        log.info("Booking successfully created!");

        return Response.created(uri).entity(new IdTO(createdBooking.getId())).build();
    }

}
