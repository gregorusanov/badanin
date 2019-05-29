package com.space.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidIdException extends RuntimeException {

    public NotValidIdException() {
        super("Not valid Id");
    }
}
