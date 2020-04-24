package com.enjoy.mathero.exceptions;


/**
 * Exception thrown by services connected with results.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class ResultServiceException extends RuntimeException {

    private static final long serialVersionUID = 1004833022866178791L;
    private String message;

    public ResultServiceException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
