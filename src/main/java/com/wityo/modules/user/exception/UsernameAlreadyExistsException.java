package com.wityo.modules.user.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1849990504237757941L;

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }

}
