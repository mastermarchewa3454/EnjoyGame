package com.enjoy.mathero.exceptions;


/**
 * Exception thrown by services connected with classes.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 2349912162554349747L;
    private String message;

    public UserServiceException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
