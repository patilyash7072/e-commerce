package com.dss.ecommerce.service;

import com.dss.ecommerce.credential.*;
import com.dss.ecommerce.dao.PaymentDao;
import com.dss.ecommerce.enums.PaymentMode;
import com.dss.ecommerce.enums.PaymentStatus;
import com.dss.ecommerce.exception.PaymentFailedException;
import com.dss.ecommerce.external.ThirdPartyPaymentSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class PaymentService {

    private final ThirdPartyPaymentSimulator thirdPartySimulator;
    private final PaymentDao paymentDao;
    Map<Integer, Integer> orderIdMappedByAmount;
    private Logger logger;

    public PaymentService(ThirdPartyPaymentSimulator thirdPartySimulator, PaymentDao paymentDao) {
        this.thirdPartySimulator = thirdPartySimulator;
        this.paymentDao = paymentDao;
        this.logger = LoggerFactory.getLogger(PaymentService.class);
    }

    public void initializeAndExecutePayment(Map<Integer, Integer> orderIdMappedByAmount, PaymentMode mode, PaymentCredential paymentCredential) throws PaymentFailedException {
        this.orderIdMappedByAmount = orderIdMappedByAmount;
        int bill = orderIdMappedByAmount
                .values()
                .stream()
                .mapToInt((amount) -> amount)
                .sum();

        logger.info("Initiating payment");
        thirdPartySimulator.initiaize(bill);
        paymentDao.add(orderIdMappedByAmount, mode);
        payUsing(mode, paymentCredential);
    }

    private void payUsing(PaymentMode paymentMode, PaymentCredential paymentCredentials) throws PaymentFailedException {
        switch (paymentMode) {
            case UPI -> {
                boolean isPaymentSuccess = thirdPartySimulator.payUsingUPI((UPICredential) paymentCredentials);
                updatePaymentStatus(isPaymentSuccess);
            }
            case DEBIT_CARD -> {
                boolean isPaymentSuccess = thirdPartySimulator.payUsingDebitCard((CardCredential) paymentCredentials);
                updatePaymentStatus(isPaymentSuccess);
            }
            case CREDIT_CARD -> {
                boolean isPaymentSuccess = thirdPartySimulator.payUsingCreditCard((CardCredential) paymentCredentials);
                updatePaymentStatus(isPaymentSuccess);
            }
            case NETBANKING -> {
                boolean isPaymentSuccess = thirdPartySimulator.payUsingNetBanking((NetBankingCredential) paymentCredentials);
                updatePaymentStatus(isPaymentSuccess);
            }
            default -> {
                boolean isPaymentSuccess = thirdPartySimulator.payUsingCOD();
                updatePaymentStatus(isPaymentSuccess);
            }
        }
    }

    private void updatePaymentStatus(boolean isPaymentSuccess) throws PaymentFailedException {
        if (!isPaymentSuccess) {
            paymentDao.updateStatus(orderIdMappedByAmount, PaymentStatus.FAILED);
            throw new PaymentFailedException("Payment failed due to invalid credential");
        }
        logger.info("Payment successful");
        paymentDao.updateStatus(orderIdMappedByAmount, PaymentStatus.SUCCESS);
    }
}
