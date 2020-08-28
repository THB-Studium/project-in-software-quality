package com.example.demo.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;

@Named
@Transactional(rollbackOn = Exception.class)
public class BookingService {
	
	@Inject
	private BookingRepository bookingRepository;
	
	
	
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
		if(bookingOp.isPresent()) {
			return bookingOp.get();
		} else {
			throw new NotFoundException(
					String.format("A booking with the id %s does not exist", bookingId.toString())
					);
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
	public Booking update(UUID bookingId, Booking newBooking) {
		Booking bookingFound = getOne(bookingId);
		newBooking.setId(bookingFound.getId());
		return bookingRepository.save(newBooking);
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

}
