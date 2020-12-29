package com.perrosv4.app.excepciones;

public class SaveFileError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SaveFileError(String msg) {
		super(msg);
	}
}
