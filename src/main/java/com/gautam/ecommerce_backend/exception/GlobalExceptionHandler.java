package com.gautam.ecommerce_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse response = new ErrorResponse(
        		HttpStatus.BAD_REQUEST.value(),
        		"Validation Failed!",
        		errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {

        ErrorResponse response = new ErrorResponse(
        		HttpStatus.BAD_REQUEST.value(),
        		"Bad Request!",
        		ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCredentialsException.class) 
    	public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {

    	    ErrorResponse response = new ErrorResponse(
    	    		HttpStatus.UNAUTHORIZED.value(), 
    	    		"Unauthorized", 
    	    		ex.getMessage());

    	    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    	
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
     ErrorResponse response = new ErrorResponse(
    		HttpStatus.INTERNAL_SERVER_ERROR.value(),
    		"Internal Server Error",
    		ex.getMessage()
    		);
     return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}