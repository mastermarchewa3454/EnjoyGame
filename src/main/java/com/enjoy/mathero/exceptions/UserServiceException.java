package com.enjoy.mathero.exceptions;

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
