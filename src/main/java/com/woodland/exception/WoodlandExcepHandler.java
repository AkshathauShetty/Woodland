package com.woodland.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WoodlandExcepHandler {

	@ExceptionHandler(value = {DataNotFound.class})
	public ResponseEntity<ErrorResponse> woodlandExceptionHandler(DataNotFound exc){
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),exc.getMessage());
		ResponseEntity<ErrorResponse> re = new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		return re;
	}
	
	@ExceptionHandler(value = {ConversionFailed.class})
	public ResponseEntity<String> woodlandExceptionHandler(ConversionFailed exc){
		
		ResponseEntity<String> re = new ResponseEntity<>(exc.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return re;
	}
	
	@ExceptionHandler(value = {InputInvalid.class})
	public ResponseEntity<String> woodlandExceptionHandler(InputInvalid exc,Throwable e){
		/*
		 * entity that is syntactically valid, but has no content or fields, is 422 Unprocessable Entity.
		 * This status code means that the server understands the content type of the request entity, 
		 * and the syntax of the request entity is correct, but it was unable to process the contained instructions. 
		 * For example, an empty JSON object {} does not provide any information for the server to act upon.
		 */
		
		ResponseEntity<String> re = new ResponseEntity<>(exc.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
		return re;
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<String> woodlandExceptionHandler(Exception exc){
		
		ResponseEntity<String> re = new ResponseEntity<>(exc.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return re;
	}
	
}



