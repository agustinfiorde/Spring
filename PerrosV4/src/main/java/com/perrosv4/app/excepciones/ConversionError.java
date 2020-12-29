package com.perrosv4.app.excepciones;

public class ConversionError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConversionError(String msg) {
		super(msg);
	}
}
