package com.trade.store.exception;

/**
 * Custom exception
 * @author Avantika
 *
 */
public class TradeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String exceptionMessage;

	private int errorCode;
	
	public TradeException(String message, int errorCode) {
		this.exceptionMessage = message;
		this.errorCode = errorCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}
