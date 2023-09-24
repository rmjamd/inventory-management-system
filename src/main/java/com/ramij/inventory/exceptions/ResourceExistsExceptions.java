package com.ramij.inventory.exceptions;

import lombok.Getter;

@Getter
public class ResourceExistsExceptions extends RuntimeException {
	public ResourceExistsExceptions (String message) {
		super(message);
	}


	public ResourceExistsExceptions (String message, Throwable cause) {
		super(message, cause);
	}
}
