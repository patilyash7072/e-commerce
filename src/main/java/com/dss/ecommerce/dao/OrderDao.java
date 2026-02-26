package com.dss.ecommerce.dao;

import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.enums.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public interface OrderDao {

    public Map<Integer, Integer> addAll(Cart cart, String customerId);

    public void updateStatus(Map<Integer, Integer> orderIdMappedByAmount, OrderStatus orderStatus);
}
