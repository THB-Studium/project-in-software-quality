package com.example.demo.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoBookingValueException extends Exception {

	private static final long serialVersionUID = 2656207163937668256L;
	
	/**
	 * Initialize Exception
	 */
	public NoBookingValueException(){
		super();
	}
	
	/**
	 * Initialize detailMessage
	 * 
	 * @param message 
	 */
	public NoBookingValueException(String message){
		super(message);
	}

}
