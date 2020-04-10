package com.enjoy.mathero.exceptions;

public class CustomLobbyServiceException extends RuntimeException {

    private static final long serialVersionUID = 8426168623877326279L;
    private String message;

    public CustomLobbyServiceException(String message){
        this.message = message;
    }
}
