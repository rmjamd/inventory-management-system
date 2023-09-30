package com.ramij.inventory.exceptions;

public class ResourceExistsException extends RuntimeException {
	public ResourceExistsException (String message) {
		super(message);
	}


	public ResourceExistsException (String message, Throwable cause) {
		super(message, cause);
	}
}
