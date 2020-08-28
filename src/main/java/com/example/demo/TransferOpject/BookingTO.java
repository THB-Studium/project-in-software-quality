package com.example.demo.TransferOpject;

import java.util.List;

import com.example.demo.model.Booking;
import com.example.demo.model.ParticipantVO;
import com.example.demo.model.SeminarVO;

public class BookingTO {
	
	private Booking newBooking;
	private SeminarVO currentSeminarVO;
	private List<ParticipantVO> participants;
	
	
	public Booking getNewBooking() {
		return newBooking;
	}
	public void setNewBooking(Booking newBooking) {
		this.newBooking = newBooking;
	}
	public SeminarVO getCurrentSeminarVO() {
		return currentSeminarVO;
	}
	public void setCurrentSeminarVO(SeminarVO currentSeminarVO) {
		this.currentSeminarVO = currentSeminarVO;
	}
	public List<ParticipantVO> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ParticipantVO> participants) {
		this.participants = participants;
	}
	
}
