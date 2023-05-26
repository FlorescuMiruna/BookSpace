package com.example.bookspaceapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class CannotSavePhotoException extends RuntimeException {
    public CannotSavePhotoException(String message) {
        super(message);
    }

    public CannotSavePhotoException() {
    }
}
