package com.gryard.basecamp.exception;

public class LoginCheckException extends Exception {
	public LoginCheckException(String msg, Throwable t) {
        super(msg, t);
    }

    public LoginCheckException(String msg) {
        super(msg);
    }

    public LoginCheckException() {
        super();
    }
}
