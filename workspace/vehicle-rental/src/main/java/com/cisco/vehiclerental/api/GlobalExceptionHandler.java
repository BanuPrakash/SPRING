package com.cisco.vehiclerental.api;

import com.cisco.vehiclerental.exceptions.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    //ResponseEntity is Response body + header
    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Object> handleVehicleNotFoundException(VehicleNotFoundException ex) {
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", "404 Resource not found!!!");
        response.put("timestamp", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //ResponseEntity is Response body + header
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,Object> response = new LinkedHashMap<>();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                        .map(exception -> exception.getDefaultMessage()).collect(Collectors.toList());

        response.put("errors", errors);
        response.put("status", "400 Bad Request!!!");
        response.put("timestamp", new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
