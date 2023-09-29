package com.ramij.inventory.exceptions;

public class ResourceException extends RuntimeException {
	public ResourceException (String message) {
		super(message);
	}


	public ResourceException (String message, Throwable cause) {
		super(message, cause);
	}
}
