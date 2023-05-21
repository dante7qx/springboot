package org.dante.springboot.exception;

public class ExportRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExportRuntimeException() {
	}

	public ExportRuntimeException(String err) {
		super(err);
	}

}
