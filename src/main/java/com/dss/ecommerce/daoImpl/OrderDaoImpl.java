package com.dss.ecommerce.daoImpl;

import com.dss.ecommerce.dao.OrderDao;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.enums.OrderStatus;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    Connection connection;

    public OrderDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Map<Integer, Integer> addAll(Cart cart, String customerId) {
        try {
            Map<Integer, Integer> orderIdMappedByAmount = new HashMap<>();
            StringBuilder sql = getAddAllSql(cart.getCartAsMap(), customerId);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.toString());

            Iterator<Map.Entry<Product, Integer>> iterator = cart.getCartAsMap().entrySet().iterator();
            while (resultSet.next() && iterator.hasNext()) {
                int id = resultSet.getInt("id");
                Map.Entry<Product, Integer> next = iterator.next();
                orderIdMappedByAmount.put(id, (next.getKey().price() * next.getValue()));
            }

            return orderIdMappedByAmount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder getAddAllSql(Map<Product, Integer> cartMap, String customerId) {
        StringBuilder sql = new StringBuilder("""
                INSERT INTO "e-commerce".tbl_order (customer_id, product_id, quantity) 
                VALUES
                """);
        cartMap.forEach(
                ((product, quantity) -> {
                    sql.append(" ( ")
                            .append(customerId)
                            .append(", ")
                            .append(product.productId())
                            .append(", " )
                            .append(quantity)
                            .append("),");
                })
        );
        sql.delete(sql.length() - 1, sql.length());
        sql.append(" RETURNING id;");
        return sql;
    }

    public void updateStatus(Map<Integer, Integer> orderIdMappedByAmount, OrderStatus orderStatus){
        try {
            StringBuilder sql = getUpdateSql(orderIdMappedByAmount);
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setString(1, orderStatus.value);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder getUpdateSql(Map<Integer, Integer> orderIdMappedByAmount) {
        StringBuilder sql = new StringBuilder("""
                UPDATE "e-commerce".tbl_order 
                SET status = ? WHERE id IN (
                """);
        orderIdMappedByAmount.keySet().forEach(
                (orderId) -> sql.append(orderId).append(", ")
        );
        sql.delete(sql.length() - 2, sql.length());
        sql.append(")");
        return sql;
    }
}
