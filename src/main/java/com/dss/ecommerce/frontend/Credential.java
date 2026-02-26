package com.dss.ecommerce.frontend;

import com.dss.ecommerce.credential.CardCredential;
import com.dss.ecommerce.credential.NetBankingCredential;
import com.dss.ecommerce.credential.PaymentCredential;
import com.dss.ecommerce.credential.UPICredential;
import com.dss.ecommerce.enums.PaymentMode;

import java.time.LocalDate;
import java.util.Scanner;

public class Credential {
    protected static PaymentCredential getCredential(PaymentMode mode) {
        switch (mode) {
            case UPI -> {return getUPICredential();}
            case DEBIT_CARD, CREDIT_CARD -> {return getCardCredential();}
            case NETBANKING -> {return getNetBankingCredential();}
            default -> {return null;}
        }
    }

    private static UPICredential getUPICredential() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter phone no linked to UPI: ");
        String phoneNo = sc.next();
        System.out.print("Enter pin: ");
        int pin = sc.nextInt();

        return new UPICredential(phoneNo, pin);
    }

    private static CardCredential getCardCredential() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter card number: ");
        String cardNo = sc.next();
        System.out.print("Enter cvv: ");
        int cvv = sc.nextInt();
        System.out.print("Enter expiry month: ");
        int month = sc.nextInt();
        System.out.print("Enter expiry year: ");
        int year = sc.nextInt();

        LocalDate expiryDate = LocalDate.of(year, month, 1);

        return new CardCredential(cardNo, cvv, expiryDate);
    }

    private static NetBankingCredential getNetBankingCredential() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username for NetBanking: ");
        String username = sc.next();
        System.out.print("Enter password: ");
        String password = sc.next();

        return new NetBankingCredential(username, password);
    }
}
