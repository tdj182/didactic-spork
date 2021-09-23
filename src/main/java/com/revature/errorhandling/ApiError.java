package com.revature.errorhandling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data // all getters and setters
public class ApiError {

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private int status;
	
	private String error;
	
	private String message;
	private String debugMessage;
	
	//there might be sub errors(we'll create a class from this)
	List<ApiSubError> subErrors = new ArrayList<>();
	

	public ApiError() {
		super();
		this.timestamp = LocalDateTime.now();
	}
	
	public ApiError(HttpStatus status) {
		super();
		this.status = status.value(); // convert the HttpStatus to an integer by capturing the value
		this.error = status.getReasonPhrase();
	}
	
	public ApiError(HttpStatus status, Throwable ex) {
		this(status); // this is constructor chaing -- we are doing everything that the above constructo is doing with this param
		this.message = "No message available";
		this.debugMessage = ex.getLocalizedMessage(); // now we dont have to keep looking at our console to figure out why things went wrong
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
		
		this(status, ex); // will trigger the above constructor
		this.message = message;
	}
	
	public void addSubError(ApiSubError error) {
		this.subErrors.add(error);
	}



	
}
