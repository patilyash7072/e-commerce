package com.dss.ecommerce.credential;

import java.time.LocalDate;

public record CardCredential(String cardNumber, int cvv, LocalDate expireDate) implements PaymentCredential{
}
