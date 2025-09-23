package com.cisco.shopapp.api;

import com.cisco.shopapp.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", ex.getMessage());
        map.put("status", "404- Resource Not Found!!!");
        map.put("timestamp", new Date());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
