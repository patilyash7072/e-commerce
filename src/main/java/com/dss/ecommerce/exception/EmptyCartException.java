package com.dss.ecommerce.exception;

public class EmptyCartException extends Exception {
    public EmptyCartException(String message) {
        super(message);
    }
}
