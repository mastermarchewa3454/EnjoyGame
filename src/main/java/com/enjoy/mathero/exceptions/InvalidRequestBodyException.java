package com.enjoy.mathero.exceptions;

import org.springframework.validation.BindingResult;


/**
 * Exception thrown when validation of request model fails.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class InvalidRequestBodyException extends RuntimeException {

    private static final long serialVersionUID = -4957730089652972107L;
    private BindingResult bindingResult;
    private String message;

    public InvalidRequestBodyException(String message){
        this.message = message;
    }

    public InvalidRequestBodyException(BindingResult bindingResult){
        this.bindingResult = bindingResult;
    }

    public InvalidRequestBodyException(String message, BindingResult bindingResult){
        this.message = message;
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult(){
        return this.bindingResult;
    }

    public String getMessage(){
        return this.message;
    }
}
