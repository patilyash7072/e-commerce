package com.dss.ecommerce.exception;

public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}
