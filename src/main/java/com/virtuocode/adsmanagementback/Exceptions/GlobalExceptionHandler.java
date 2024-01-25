package com.virtuocode.adsmanagementback.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEntityNotFoundException(EntityNotFoundException e){
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("message", e.getMessage());
        return res;
    }



    @ExceptionHandler(EntityFailedToSaveException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEntityFailedToSaveException(EntityFailedToSaveException e){
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("message", e.getMessage());
        res.put("entity",e.getEntity());
        return res;
    }



 }
