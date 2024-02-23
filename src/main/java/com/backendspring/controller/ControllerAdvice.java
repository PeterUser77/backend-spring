package com.backendspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.backendspring.exception.RecordNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {
    
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(
        RecordNotFoundException exception
    ) {
        return exception.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception
    ) {
        return exception.getBindingResult().getFieldErrors()
            .stream()
            .map(error -> error.getField() + " " + error.getDefaultMessage())
            .reduce("", (acc, error) -> acc + error + "\n");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(
        ConstraintViolationException exception
    ) {
        return exception.getConstraintViolations()
            .stream()
            .map(error -> error.getPropertyPath() + " " + error.getMessage())
            .reduce("", (acc, error) -> acc + error + "\n"); 
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException exception
    ) {
        if(exception != null && exception.getRequiredType() != null) {
            String type = exception.getRequiredType().getName();
            String[] typeParts = type.split("\\.");
            String typeName = typeParts[typeParts.length - 1];

            return exception.getName() + " should be of type " + typeName;
        }
        return "Argument type is not valid.";
    }

}
