package com.mycompany.exceptionHandler;

import java.time.LocalDateTime;
import javax.ws.rs.core.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.mycompany.dto.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(BuyerNotFoundException.class)
	public final ResponseEntity<Object> BuyerNotFoundException(BuyerNotFoundException ex, WebRequest request) {
		return buildResponseEntity(new ErrorResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false), HttpStatus.NOT_FOUND));
	}

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders header, HttpStatus status, WebRequest request) {
		String msg = "Malformed Request";
		logger.info(msg);
		return buildResponseEntity(new ErrorResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false), HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(DBException.class)
	public final ResponseEntity<Object> dBException(DBException ex, WebRequest request) {
		return buildResponseEntity(new ErrorResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR));
	}

	private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
		return new ResponseEntity(errorResponse, errorResponse.getStatus());
	}

}
