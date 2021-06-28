package com.sixfold.flightplanner.service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class ErrorMessage {
	private final HttpStatus httpStatus;
	private final String timestamp;
	private final String description;
	public String message;

	public ErrorMessage(String message, Throwable throwable, HttpStatus httpStatus, String timestamp, String description) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
		this.description = description;
	}
}
