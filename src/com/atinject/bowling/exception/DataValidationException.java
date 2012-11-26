package com.atinject.bowling.exception;

public class DataValidationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataValidationException(String msg) {
		super(msg);
	}

}
