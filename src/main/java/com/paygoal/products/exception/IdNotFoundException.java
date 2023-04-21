package com.paygoal.products.exception;

import java.io.Serial;

public class IdNotFoundException extends Exception{

    public IdNotFoundException(String message){
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Serial
    private static final long serialVersionUID = 9142958382144731855L;
}
