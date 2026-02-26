package com.dss.ecommerce.enums;

public enum OrderStatus {
    PENDING("PENDING"), SUCCESS("SUCCESS"), FAILED("FAILED");
    public String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
