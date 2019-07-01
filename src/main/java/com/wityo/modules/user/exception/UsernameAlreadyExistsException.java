package com.wityo.modules.user.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

	public UsernameAlreadyExistsException(String msg) {
		super(msg);
	}
	
}
