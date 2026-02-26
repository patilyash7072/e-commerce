package com.dss.ecommerce.daoImpl;

import com.dss.ecommerce.dao.PaymentDao;
import com.dss.ecommerce.enums.PaymentMode;
import com.dss.ecommerce.enums.PaymentStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class PaymentDaoImpl implements PaymentDao {
    Connection connection;

    public PaymentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void add(Map<Integer, Integer> orderIdMappedByAmount, PaymentMode mode) {
        try {
            StringBuilder sql = getAddSql(orderIdMappedByAmount, mode);
            Statement statement = connection.createStatement();
            statement.execute(sql.toString());
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder getAddSql(Map<Integer, Integer> orderIdMappedByAmount, PaymentMode mode) {
        StringBuilder sql = new StringBuilder("""
                INSERT INTO "e-commerce".tbl_payment (order_id, payment_method, amount) 
                VALUES 
                """);
        for (Map.Entry<Integer, Integer> entry : orderIdMappedByAmount.entrySet()) {
            Integer order_id = entry.getKey();
            Integer amount = entry.getValue();
            sql.append(" (")
                    .append(order_id)
                    .append(", \'")
                    .append(mode.value)
                    .append("\', ")
                    .append(amount)
                    .append("),");
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append(";");
        return sql;
    }

    public void updateStatus(Map<Integer, Integer> orderIdMappedByAmount, PaymentStatus paymentStatus){
        try {
            StringBuilder sql = getUpdateSql(orderIdMappedByAmount);
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setString(1, paymentStatus.value);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder getUpdateSql(Map<Integer, Integer> orderIdMappedByAmount) {
        StringBuilder sql = new StringBuilder("""
                UPDATE "e-commerce".tbl_payment SET status = ? 
                WHERE order_id IN (
                """);
        orderIdMappedByAmount
                .keySet()
                .forEach(
                        (orderId) -> sql.append(orderId).append(", "));
        sql.delete(sql.length() - 2, sql.length());
        sql.append(")");
        return sql;
    }
}
