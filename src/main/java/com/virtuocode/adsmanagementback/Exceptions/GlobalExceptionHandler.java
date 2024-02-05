package com.virtuocode.adsmanagementback.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, Object> res = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        res.put("timestamp", LocalDateTime.now().format(formatter));
        res.put("message", e.getMessage());
        return res;
    }


    @ExceptionHandler(EntityFailedToSaveException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEntityFailedToSaveException(EntityFailedToSaveException e) {
        Map<String, Object> res = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        res.put("timestamp", LocalDateTime.now().format(formatter));
        res.put("message", e.getMessage());
//        res.put("entity",e.getEntity());
        return res;
    }

    @ExceptionHandler(EntityFailedToDeleteException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEntityFailedToDeleteException(EntityFailedToDeleteException e) {
        Map<String, Object> res = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        res.put("timestamp", LocalDateTime.now().format(formatter));
        res.put("message", e.getMessage());
//        res.put("entityId",e.getEntity());
        return res;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEntityValidationException(BindingResult bindingResult) {
        Map<String, Object> res = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        res.put("timestamp", LocalDateTime.now().format(formatter));
        res.put("message", "Validation failed. Please check your request.");
        List<String> validationErrors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            validationErrors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        res.put("validationErrors", validationErrors);
        return res;
    }


}
