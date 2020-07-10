package com.javapropjects.ccf.exceptions;

import com.javapropjects.ccf.model.ErrorDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetails> handleInvalidRequestException(InvalidRequestException exception) {
        ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRouteNotFoundException(RouteNotFoundException exception) {
        ErrorDetails error = new ErrorDetails(HttpStatus.NOT_FOUND.toString(), exception.getMessage());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGeneralError(Exception ex) {
        ErrorDetails error = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}