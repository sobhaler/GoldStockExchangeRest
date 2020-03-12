package com.mycompany.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-YYYY hh:mm:ss ")
	private LocalDateTime timestamp=null;
	private String message =null;
	private String details  =null;
	private HttpStatus status= null;
	
	public ErrorResponse(LocalDateTime timestamp, String message, String details, HttpStatus status) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
