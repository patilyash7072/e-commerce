package com.dss.ecommerce.service;

import com.dss.ecommerce.dao.OrderDao;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.enums.OrderStatus;
import com.dss.ecommerce.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class OrderService {

    private final OrderDao orderDao;
    private Logger logger;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.logger = LoggerFactory.getLogger(OrderService.class);
    }

    public Map<Integer, Integer> add(Cart cart) throws UserNotFoundException {
        logger.info("Adding new orders");
        String userId = LoginService.getUserId();
        return orderDao.addAll(cart, userId);
    }

    public void updateStatus(Map<Integer, Integer> orderIdMappedByAmount, OrderStatus orderStatus) {
        orderDao.updateStatus(orderIdMappedByAmount, orderStatus);
        logger.info("Order status updated to {}", orderStatus);
    }
}
