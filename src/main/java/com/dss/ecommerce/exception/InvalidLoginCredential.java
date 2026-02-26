package com.dss.ecommerce.exception;

public class InvalidLoginCredential extends Exception {
    public InvalidLoginCredential(String message) {
        super(message);
    }
}
