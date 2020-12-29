package com.perrosv4.app.excepciones;

import java.util.Date;

import lombok.Getter;

public class ErrorMessage {
	
	private @Getter int statusCode;
	private @Getter Date timestamp;
	private @Getter String message;
	private @Getter String description;

	public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}

}
