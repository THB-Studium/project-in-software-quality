package com.example.demo.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.example.demo.constant.StateOfSeminarVO;
import com.example.demo.model.Booking;
import com.example.demo.model.ParticipantVO;
import com.example.demo.model.SeminarVO;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.SeminarVORepository;
import com.example.demo.service.exception.NoParticipantsException;
import com.example.demo.service.exception.NoSeminarException;
import com.example.demo.service.exception.ResourceNotFoundException;

/**
 * 
 * @author Steve Ngalamo
 * @version 1.0 30.08.2020
 */
@Named
@Transactional(rollbackOn = Exception.class)
public class BookingService implements IBooking {

    @Inject
    private BookingRepository bookingRepository;
    @Inject
    private SeminarVORepository seminarVORepository;
    @Inject
    private SeminarService seminarService;
    
    

    /**
     * TO GET ALL BOOKINGS
     * 
     * @return
     */
    public Set<Booking> listAll() {
        return bookingRepository.findAll().stream().collect(Collectors.toSet());
    }

    /**
     * TO GET ONE BOOKING BY BOOKING´S ID
     * 
     * @param bookingId
     * @return
     */
    public Booking getOne(UUID bookingId) {
        Optional<Booking> bookingOp = bookingRepository.findById(bookingId);
        if (bookingOp.isPresent()) {
            return bookingOp.get();
        } else {
            throw new ResourceNotFoundException(
                    String.format("A booking with the id %s does not exist", bookingId.toString()));
        }
    }

    /**
     * TO CREATE A NEW BOOKING
     * 
     * @param newBooking
     * @return
     */
    public Booking create(Booking newBooking) {
        newBooking.setId(null);
        return bookingRepository.save(newBooking);
    }

    /**
     * TO UPDATE A NEW BOOKING
     * 
     * @param bookingId
     * @param newBooking
     * @return
     */
    public void update(UUID bookingId, Booking newBooking) {
        getOne(bookingId);
        newBooking.setId(bookingId);
        bookingRepository.save(newBooking);
    }

    /**
     * TO DELETE A BOOKING
     * 
     * @param bookingId
     */
    public void delete(UUID bookingId) {
        Booking bookingFound = getOne(bookingId);
        bookingRepository.delete(bookingFound);
    }

    // about
    // Seminar:.............................................................

    /**
     * TO START A SEMINAR
     * 
     * @param seminarId
     * @return
     * @throws NoSeminarException
     */
    public SeminarVO startNewSeminar(SeminarVO newSeminar) throws NoSeminarException {
        SeminarVO createdSeminar = seminarService.create(newSeminar);
        Booking newBooking = new Booking(createdSeminar);
        return bookingRepository.save(newBooking).getCurrentSeminar();

    }

    /**
     * TO ADD PARTICIPANTS TO A SEMINAR
     * 
     * @param bookingId
     * @param participants
     * @throws IllegalStateException 
     * @throws NullPointerException 
     * @throws NoSeminarException
     * @throws NoParticipantsException
     */
    public void addParticipants(UUID bookingId, Set<ParticipantVO> participants) 
            throws NullPointerException, IllegalStateException, NoSeminarException {
        SeminarVO seminarFound = getOne(bookingId).getCurrentSeminar();
        seminarService.addParticipants(seminarFound.getId(), participants);
    }

    /**
     * TO DELETE PARTICIPANTS TO A SEMINAR
     * 
     * @param bookingId
     * @param participants
     * @throws IllegalStateException 
     * @throws NullPointerException 
     * @throws NoSeminarException
     * @throws NoParticipantsException
     */
    public void deleteParticipants(UUID bookingId, Set<ParticipantVO> participants) 
            throws NullPointerException, IllegalStateException, NoSeminarException {
        SeminarVO seminarFound = getOne(bookingId).getCurrentSeminar();
        seminarService.deleteParticipants(seminarFound.getId(), participants);
    }

    /**
     * TO FINISH A SEMINAR
     * 
     * @param seminarId
     * @throws NullPointerException 
     * @throws NoSeminarException
     */
    public void finishSeminar(UUID bookingId) throws NoSeminarException, IllegalStateException {
        Booking booking = getOne(bookingId);
        if (booking != null) {
            if (booking.getCurrentSeminar() != null) {
                booking.getCurrentSeminar().setState(StateOfSeminarVO.FINISHED);
                seminarVORepository.save(booking.getCurrentSeminar());
            } else {
                throw new NoSeminarException (
                        String.format("There is no current seminar for the booking with a the id: %s.", bookingId.toString()));
            }
        } else {
            throw new IllegalStateException(
                    String.format("There is no booking with a the id: %s.", bookingId.toString()));
        }
    }
    
    
    /**
     * TO SORT PARTICIPANTS OF THE CURRENT SEMINAR OF A BOOKING
     * 
     * @param seminarId
     * @throws NotFoundException
     * @throws NoParticipantsException 
     * @throws NoSeminarException
     * @throws IllegalStateException
     */
    public Set<ParticipantVO> sort(UUID bookingId) 
            throws NotFoundException, NoParticipantsException, NoSeminarException, IllegalStateException {
        Booking booking = getOne(bookingId);
        Set<ParticipantVO> participants = new HashSet<ParticipantVO>();
        
        if (booking != null) {
            if (booking.getCurrentSeminar() != null) {
                participants = booking.getCurrentSeminar().getParticipants();
                if(participants != null) 
                    Collections.sort(participants.stream().collect(Collectors.toList()));
                else 
                    throw new NoParticipantsException(
                            String.format("There is no participants for the booking with the id = %s.", bookingId.toString()));
            } else {
                throw new NoSeminarException (
                        String.format("There is no seminar for the booking with the id = %s.", bookingId.toString()));
            }
        } else {
            throw new IllegalStateException(
                    String.format("There is no booking with the id: %s.", bookingId.toString()));
        }        

        return participants;
    }

}
