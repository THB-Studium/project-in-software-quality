package com.example.demo.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSeminarException extends RuntimeException {
	private static final long serialVersionUID = -6889739443415526906L;

        public NoSeminarException() {
                super();
        }

        public NoSeminarException(String message, Throwable cause) {
                super(message, cause);
        }

        public NoSeminarException(String message) {
                super(message);
        }

        public NoSeminarException(Throwable cause) {
                super(cause);
        }
}
