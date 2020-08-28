package com.example.demo.service.exception;




public class NoSeminarException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6889739443415526906L;

	/**
	 * Initialize Exception
	 */
	public NoSeminarException(){
		super();
	}
	
	/**
	 * Initialize detailMessage
	 * 
	 * @param message 
	 */
	public NoSeminarException(String message){
		super(message);
	}
}
