package com.enjoy.mathero.exceptions;


/**
 * Exception thrown by services connected with roles.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class RoleServiceException extends RuntimeException {
    private static final long serialVersionUID = 5797516720161153923L;

    private String message;

    public RoleServiceException(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
