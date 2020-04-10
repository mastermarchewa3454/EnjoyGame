package com.enjoy.mathero.exceptions;

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
