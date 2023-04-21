package com.paygoal.products.exception;

import java.io.Serial;

public class NameAlreadyExistsException extends Exception {

    public NameAlreadyExistsException(String message) {
        super(message);
    }

    public NameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Serial
    private static final long serialVersionUID = 257461574467144150L;
}
