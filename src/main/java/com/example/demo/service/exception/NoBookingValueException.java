package com.example.demo.service.exception;

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
