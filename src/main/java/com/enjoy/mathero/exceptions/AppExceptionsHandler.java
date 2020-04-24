package com.enjoy.mathero.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.*;


/**
 * The AppExceptionsHandler is a class to handle various exceptions that may occur during the execution of SpringBootApplication.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@ControllerAdvice
public class AppExceptionsHandler {

    @Autowired
    MessageSource messageSource;


    /**
     * Method that handles UserServiceException
     *
     * @param e Exception
     * @param request WebRequest
     * @return response entity with errors and timestamp
     */
    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException e, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", e.getMessage());

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that handles ClassServiceException
     *
     * @param e Exception
     * @param request WebRequest
     * @return response entity with errors and timestamp
     */
    @ExceptionHandler(value = {ClassServiceException.class})
    public ResponseEntity<Object> handleClassServiceException(ClassServiceException e, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", e.getMessage());

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that handles ResultServiceException
     *
     * @param e Exception
     * @param request WebRequest
     * @return response entity with errors and timestamp
     */
    @ExceptionHandler(value = {ResultServiceException.class})
    public ResponseEntity<Object> handleResultServiceException(ResultServiceException e, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", e.getMessage());

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that handles CustomLobbyServiceException
     *
     * @param e Exception
     * @param request WebRequest
     * @return response entity with errors and timestamp
     */
    @ExceptionHandler(value = {CustomLobbyServiceException.class})
    public ResponseEntity<Object> handleCustomLobbyServiceException(CustomLobbyServiceException e, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", e.getMessage());

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that handles RoleServiceException
     *
     * @param e Exception
     * @param request WebRequest
     * @return response entity with errors and timestamp
     */
    @ExceptionHandler(value = {RoleServiceException.class})
    public ResponseEntity<Object> handleRoleServiceException(RoleServiceException e, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", e.getMessage());

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that deals with Exceptions not handled by other methods.
     *
     * @param e Exception
     * @param request WebRequest
     * @return response entity with errors and timestamp
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception e, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", e.getMessage());


        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that handles InvalidRequestBodyException
     *
     * @param e Exception
     * @param request WebRequest
     * @return response entity with errors and timestamp
     */
    @ExceptionHandler(value = {InvalidRequestBodyException.class})
    public ResponseEntity<Object> handleInvalidRequestBodyException(InvalidRequestBodyException e, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<String> errorMessages = new ArrayList<>();

        for(FieldError error: errors){
            errorMessages.add(error.getField() + ": " + messageSource.getMessage(error.getCode(), new Object[0], new Locale("")));
        }

        body.put("errors", errorMessages);

        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
