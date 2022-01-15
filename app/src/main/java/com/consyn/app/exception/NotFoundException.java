package com.consyn.app.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	public NotFoundException(Throwable cause) {
		super(cause);

	}

	public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public NotFoundException(String message) {
		super(message);

	}

	public HttpStatus getStatus() {
		 return HttpStatus.NOT_FOUND;
	}

}
