package com.docker.k8s.departmentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalEntityExceptionHandler extends ResponseEntityExceptionHandler {

    ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception){
        Map<String, Object> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
