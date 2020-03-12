package com.mycompany.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DBException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DBException(String message) {
		super(message);
	}
}
