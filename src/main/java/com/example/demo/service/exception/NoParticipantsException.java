package com.example.demo.service.exception;




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
