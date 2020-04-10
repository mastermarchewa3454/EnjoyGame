package com.enjoy.mathero.exceptions;

public class ClassServiceException extends RuntimeException {

    private static final long serialVersionUID = -1334126784948113121L;
    private String message;

    public ClassServiceException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
