package com.example.demo.rest.bookings;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Booking;
import com.example.demo.model.ParticipantVO;
import com.example.demo.rest.ApiConstants;
import com.example.demo.service.BookingService;
import com.example.demo.service.exception.NoParticipantsException;
import com.example.demo.service.exception.NoSeminarException;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@RestController
@RequestMapping(ApiConstants.BOOKING_ITEM)
public class BookingResource {
    private static final Logger log = LoggerFactory.getLogger(BookingRootResource.class);

    @Autowired
    private BookingService bookingService;

    /**
     * TO GET ONE BOOKING BY HIS ID: path: "/api/bookings/{bookingId}"
     * 
     * @param bookingId
     * @return
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Booking getOne(@PathVariable("bookingId") UUID bookingId) {
        Booking bookingFound = bookingService.getOne(bookingId);
        log.info(
                String.format("The Booking with the id=%s has been found!", bookingId.toString()));
        return bookingFound;
    }

    /**
     * TO UPDATE A BOOKING path: "/api/bookings/{bookingId}"
     * 
     * @param bookingId
     * @param update
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("bookingId") UUID bookingId, @RequestBody Booking update) {
        bookingService.update(bookingId, update);
        log.info(String.format("The Booking with the id=%s has been successfully updated!", bookingId.toString()));
    }

    /**
     * TO DELETE A BOOKING "/api/bookings/{bookingId}"
     * 
     * @param bookingId
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("bookingId") UUID bookingId) {
        bookingService.delete(bookingId);
        log.info(String.format("The Booking with the id=%s has been successfully updated!", bookingId.toString()));
    }

    // about
    // Seminar:.................................................................

    /**
     * TO FINISH A SEMINAR 
     * "/api/bookings/{bookingId}/seminar/finish"
     * 
     * @param seminarId
     * @throws IllegalStateException
     * @throws NullPointerException
     * @throws NoSeminarException
     */
    @RequestMapping(
            path = "/seminar/finish", 
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void finishSeminar(@PathVariable("bookingId") UUID bookingId)
            throws IllegalStateException, NoSeminarException {
        bookingService.finishSeminar(bookingId);
        log.info(String.format("The current seminar of the booking id=%s has been successfully finished!", bookingId.toString()));
    }

    // about
    // Participant:.............................................................

    /**
     * TO ADD PARTICIPANTS TO A SEMINAR
     * "/api/bookings/{bookingId}/participants/add"
     * 
     * @param bookingId
     * @param participants
     * @throws IllegalStateException
     * @throws NullPointerException
     * @throws NoSeminarException
     * @throws NoParticipantsException
     */
    @RequestMapping(
            path = "/participants/add", 
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addParticipants(@PathVariable("bookingId") UUID bookingId, @RequestBody Set<ParticipantVO> participants)
            throws NullPointerException, IllegalStateException, NoSeminarException {
        bookingService.addParticipants(bookingId, participants);
        log.info(String.format(
                "Participants have been added in the currennt seminar of the booking id=%s has been successfully finished!",
                bookingId.toString()));
    }

    /**
     * TO DELETE PARTICIPANTS TO A SEMINAR
     * "/api/bookings/{bookingId}/participants/delete"
     * 
     * @param bookingId
     * @param participants
     * @throws IllegalStateException
     * @throws NullPointerException
     * @throws NoSeminarException
     * @throws NoParticipantsException
     */
    @RequestMapping(path = "/participants/delete", method = RequestMethod.DELETE)
    public void deleteParticipants(@PathVariable("bookingId") UUID bookingId, @RequestBody Set<ParticipantVO> participants)
            throws NullPointerException, IllegalStateException, NoSeminarException {
        bookingService.deleteParticipants(bookingId, participants);
        log.info(String.format(
                "Participants have been added in the currennt seminar of the booking id=%s has been successfully finished!",
                bookingId.toString()));
    }

    /**
     * TO SORT PARTICIPANTS OF THE CURRENT SEMINAR OF A BOOKING
     * "/api/bookings/{bookingId}/participants/sort"
     * 
     * @param bookingId
     * @throws NoSeminarException
     * @throws NoParticipantsException
     * @throws IllegalStateException
     */
    @RequestMapping(path = "/participants/sort", method = RequestMethod.DELETE)
    public Set<ParticipantVO> sort(@PathVariable("bookingId") UUID bookingId)
            throws IllegalStateException, NoParticipantsException, NoSeminarException {
        Set<ParticipantVO> sorted = bookingService.sort(bookingId);
        log.info(
                String.format("The PArticipants for the booking with the id=%s has been successfully sorted!",
                        bookingId.toString()));
        return sorted;
    }

}
