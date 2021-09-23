package com.revature.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.revature.errorhandling.ApiError;
import com.revature.errorhandling.ApiValidationError;

//Tell Spring that this Advice is going to intercept all HTTP Requests that hit our Controller layer

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	/**
	 * Intercept exceptions cause by JHibernate Validation
	 * 
	 * What happens if a user users a POST request to send an invalid User object
	 * which defies some Validation that we set up in the model?
	 */
	

	// Custom method that's going to help us send back the ApiError AS a response entity
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String error = "Request Failed validation";
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
		
		// Next we can find out exactly WHAT went wrong? was the password not valid? was it the firstName length?
		
		// 1. capture the MethodArgumentNotValidException, and iterate over all the field errors
		for(FieldError e: ex.getFieldErrors()) {
			apiError.addSubError(new ApiValidationError(e.getObjectName(), e.getDefaultMessage(), e.getField(), e.getRejectedValue()));
		}

		// send back api error as ResponseEntity
		return buildResponseEntity(apiError); // look above for this method
	}
	
	/**
	 * Intercepts exceptions that are caused by invalid JSON
	 * Send back a 400 indication Client-side error
	 */
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		// PEBKAC or maybe your front end isn't properly formatting the json objects to be sent back
		String error = "Malformed JSON request!";
		
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex)); // look above for this method
	}
}
