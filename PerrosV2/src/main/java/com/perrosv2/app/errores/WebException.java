package com.perrosv2.app.errores;

public class WebException extends Exception {

	private static final long serialVersionUID = 7883636384872015753L;

	public WebException(String msn) {
        super(msn);
    }

}