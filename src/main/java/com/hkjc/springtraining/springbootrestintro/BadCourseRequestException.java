package com.hkjc.springtraining.springbootrestintro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadCourseRequestException extends RuntimeException {
    public BadCourseRequestException(String msg) {
        super(msg);
    }
}
