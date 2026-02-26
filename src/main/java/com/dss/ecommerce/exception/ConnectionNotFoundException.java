package com.dss.ecommerce.exception;

public class ConnectionNotFoundException extends RuntimeException {
    public ConnectionNotFoundException(String message) {
        super(message);
    }
}
