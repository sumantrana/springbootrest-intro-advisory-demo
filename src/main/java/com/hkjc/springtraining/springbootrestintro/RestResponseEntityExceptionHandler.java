package com.hkjc.springtraining.springbootrestintro;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Error> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fle -> new Error(fle.getField(), fle.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(value
            = { CourseNotFoundException.class })
    protected ResponseEntity<Object> handleSomeError(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific - ";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyOfResponse + ex.getMessage());
    }

}
