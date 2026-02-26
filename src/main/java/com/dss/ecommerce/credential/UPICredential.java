package com.dss.ecommerce.credential;

public record UPICredential(String phoneNumber, int pin) implements PaymentCredential{
}
