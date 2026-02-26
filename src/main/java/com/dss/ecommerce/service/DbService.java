package com.dss.ecommerce.service;

import com.dss.ecommerce.exception.ConnectionNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DbService implements AutoCloseable{

    static Map<String, Connection> userMappedByConnection = new HashMap<>();

    public static Connection getConnection(){
        String threadName = Thread.currentThread().getName();
        Connection connection = userMappedByConnection.get(threadName);
        if (connection == null) {
            connection = DbService.generateConnection();
            userMappedByConnection.put(threadName, connection);
        }
        return connection;
    }

    private static Connection generateConnection() {
        String url = "jdbc:postgresql://172.16.1.195:5331/dbkdemo";
        String user = "dbkuser";
        String password = "Fy2andZt";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static void commit() {
        String threadName = Thread.currentThread().getName();
        Connection connection = userMappedByConnection.get(threadName);
        if (connection == null) {
            throw new ConnectionNotFoundException(Thread.currentThread().getName() + " never initialized connection");
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static void rollback() {
        String threadName = Thread.currentThread().getName();
        Connection connection = userMappedByConnection.get(threadName);
        if (connection == null) {
            throw new ConnectionNotFoundException(Thread.currentThread().getName() + " never initialized connection");
        }
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        String threadName = Thread.currentThread().getName();
        Connection connection = userMappedByConnection.get(threadName);
        if (connection == null) {
            throw new ConnectionNotFoundException(Thread.currentThread().getName() + " never initialized connection");
        }
        connection.close();
    }
}
