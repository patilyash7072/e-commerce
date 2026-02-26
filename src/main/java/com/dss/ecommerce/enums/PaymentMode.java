package com.dss.ecommerce.enums;

public enum PaymentMode {
    COD("COD"), UPI("UPI"), CREDIT_CARD("CREDIT CARD"), DEBIT_CARD("DEBIT CARD"), NETBANKING("NET BANKING");

    public final String value;

    PaymentMode(String value) {
        this.value = value;
    }
}
