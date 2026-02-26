package com.dss.ecommerce.external;

import com.dss.ecommerce.credential.CardCredential;
import com.dss.ecommerce.credential.NetBankingCredential;
import com.dss.ecommerce.credential.UPICredential;

public class ThirdPartyPaymentSimulator {

    public void initiaize(int bill) {
    }

    public boolean payUsingUPI(UPICredential credential) {
        simulatePaymentProcessing();
        return simulateSuccesAndFailedPayment();
    }

    public boolean payUsingDebitCard(CardCredential credential) {
        simulatePaymentProcessing();
        return simulateSuccesAndFailedPayment();
    }

    public boolean payUsingCreditCard(CardCredential credential) {
        simulatePaymentProcessing();
        return simulateSuccesAndFailedPayment();
    }

    public boolean payUsingNetBanking(NetBankingCredential credential) {
        simulatePaymentProcessing();
        return simulateSuccesAndFailedPayment();
    }

    public boolean payUsingCOD() {
        return true;
    }

    private static void simulatePaymentProcessing() {
        try {
//            int randomWaitingTime = (int) (Math.random() * 10000);
            int randomWaitingTime = 10000;
            Thread.sleep(randomWaitingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean simulateSuccesAndFailedPayment() {
        double random = Math.random();
        return random > 0.5;
    }
}
