package com.example.demo.repository;

import java.util.Set;
import java.util.UUID;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.model.Booking;

@Named
public interface BookingRepository
	extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {
    
    public Booking findOneByCurrentSeminarId(UUID semminarId);
    public Set<Booking> findAllByCurrentSeminarId(UUID semminarId);
	
}
