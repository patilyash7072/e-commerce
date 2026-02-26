package com.dss.ecommerce.entity;

import com.dss.ecommerce.enums.OrderStatus;

import java.sql.Date;

public record Order(int id, int customer_id, int product_id, int quantity, OrderStatus status, Date created_at) {
}
