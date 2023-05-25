package com.example.bookspaceapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e){
        Map<String, String> responseParameters = new HashMap<>();
        responseParameters.put("Reason: ", e.getMessage());
        responseParameters.put("DateTime: ", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseParameters);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<?> handlerInternalErrorException(Exception e){
        Map<String, String> responseParameters = new HashMap<>();
        responseParameters.put("Reason: ", e.getMessage());
        responseParameters.put("DateTime: ", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseParameters);
    }

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ExceptionHandler(AlreadyExistingException.class)
    public ResponseEntity<?> handlerAlreadyExistingException(Exception e){
        Map<String, String> responseParameters = new HashMap<>();
        responseParameters.put("Reason: ", e.getMessage());
        responseParameters.put("DateTime: ", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseParameters);
    }
}