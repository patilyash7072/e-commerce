package com.dss.ecommerce.enums;

public enum PaymentStatus {
    PENDING("PENDING"), SUCCESS("SUCCESS"), FAILED("FAILED");
    public String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
