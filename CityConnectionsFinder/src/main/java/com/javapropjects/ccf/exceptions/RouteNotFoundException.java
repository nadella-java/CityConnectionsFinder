package com.javapropjects.ccf.exceptions;

public class RouteNotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public RouteNotFoundException(String message) {
        super(message);
    }
}