package com.dss.ecommerce.service;

import com.dss.ecommerce.credential.PaymentCredential;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.enums.OrderStatus;
import com.dss.ecommerce.enums.PaymentMode;
import com.dss.ecommerce.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class CheckoutService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final OrderService orderService;
    private Logger logger;

    public CheckoutService(PaymentService paymentService, InventoryService inventoryService, OrderService orderService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.orderService = orderService;
        this.logger = LoggerFactory.getLogger(CheckoutService.class);
    }

    //1. Checkout using Payment Partner
    public void checkout(Cart cart, PaymentMode mode, PaymentCredential credential) throws InsufficientStockException, ProductNotFoundException, PaymentFailedException, EmptyCartException, UserNotFoundException {
        logger.info("Initiating checkout");
        inventoryService.checkAndUpdate(cart);
        Map<Integer, Integer> orderIdMappedByAmount = orderService.add(cart);
        try {
            paymentService.initializeAndExecutePayment(orderIdMappedByAmount, mode, credential);
        } catch (PaymentFailedException e) {
            orderService.updateStatus(orderIdMappedByAmount, OrderStatus.FAILED);
            throw new PaymentFailedException("Payment failed due to invalid credentials");
        }
        orderService.updateStatus(orderIdMappedByAmount, OrderStatus.SUCCESS);
    }
}
