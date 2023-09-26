package com.ramij.inventory.handlers;

import com.ramij.inventory.exceptions.ResourceExistsException;
import com.ramij.inventory.model.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandlers {
	@ExceptionHandler(ResourceExistsException.class)
	public ResponseEntity <Response> throwResourceExistsException (ResourceExistsException ex) {
		return new ResponseEntity <>(new Response().setMessage(ex.getMessage()).setStatus("failed"), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity <Response> throwEntityNotFoundExceptions (EntityNotFoundException ex) {
		return new ResponseEntity <>(new Response().setMessage(ex.getMessage()).setStatus("failed"), HttpStatus.BAD_REQUEST);
	}


}
