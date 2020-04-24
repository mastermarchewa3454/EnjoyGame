package com.enjoy.mathero.exceptions;

/**
 * Exception thrown by services connected with custom lobbies.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class CustomLobbyServiceException extends RuntimeException {

    private static final long serialVersionUID = 8426168623877326279L;
    private String message;

    public CustomLobbyServiceException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
