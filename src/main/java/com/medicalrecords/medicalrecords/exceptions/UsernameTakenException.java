package com.medicalrecords.medicalrecords.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "This username is already taken!")
public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException() {
        super();
    }
}
