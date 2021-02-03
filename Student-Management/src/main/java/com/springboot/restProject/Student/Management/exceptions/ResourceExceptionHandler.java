package com.springboot.restProject.Student.Management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ResourceExceptionHandler
{
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e)
    {
        ResourceException r = new ResourceException(
                new Date(),e.getMessage());
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }
}
