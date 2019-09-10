package com.wityo.modules.cart.exception;

public class NoCartFoundException extends RuntimeException {

    private static final long serialVersionUID = 3884938158189077541L;

    public NoCartFoundException(String message) {
        super(message);
    }
}
