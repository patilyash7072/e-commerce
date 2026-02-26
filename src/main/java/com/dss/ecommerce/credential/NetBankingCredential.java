package com.dss.ecommerce.credential;

public record NetBankingCredential(String username, String password) implements PaymentCredential{
}
