package com.example.demo.service;

import java.util.Set;
import java.util.UUID;

import com.example.demo.model.ParticipantVO;
import com.example.demo.model.SeminarVO;
import com.example.demo.service.exception.NoParticipantsException;
import com.example.demo.service.exception.NoSeminarException;


public interface IBooking {
	
	public SeminarVO startNewSeminar(SeminarVO newSeminar) throws NoSeminarException;
	
	public void addParticipants(UUID bookingId, Set<ParticipantVO> participants) throws NullPointerException, NoSeminarException, IllegalStateException ;
	
	public void deleteParticipants(UUID bookingId, Set<ParticipantVO> participants) throws NullPointerException, NoSeminarException, IllegalStateException ;
	
	public void finishSeminar(UUID bookingId) throws NoSeminarException, IllegalStateException ;
	
	public Set<ParticipantVO> sort(UUID bookingId) throws NoParticipantsException, NoSeminarException, IllegalStateException;

}
