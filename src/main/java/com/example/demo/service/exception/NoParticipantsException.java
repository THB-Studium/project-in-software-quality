package com.example.demo.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoParticipantsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6889739443415526906L;

	/**
	 * Initialize Exception
	 */
	public NoParticipantsException(){
		super();
	}
	
	/**
	 * Initialize detailMessage
	 * 
	 * @param message 
	 */
	public NoParticipantsException(String message){
		super(message);
	}
}
