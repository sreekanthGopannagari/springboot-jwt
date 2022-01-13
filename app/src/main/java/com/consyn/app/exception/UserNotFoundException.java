package com.consyn.app.exception;

public class UserNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);

	}

}
