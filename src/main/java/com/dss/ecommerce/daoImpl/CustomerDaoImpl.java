package com.dss.ecommerce.daoImpl;

import com.dss.ecommerce.dao.CustomerDao;
import com.dss.ecommerce.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private final Connection connection;

    public CustomerDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void add(Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    INSERT INTO "e-commerce".tbl_customer(first_name, last_name, address, phone) 
                    VALUES (?, ?, ?);
                    """);
            preparedStatement.setString(1, customer.firstName());
            preparedStatement.setString(2, customer.lastname());
            preparedStatement.setString(3, customer.address());
            preparedStatement.setString(4, customer.phone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customerList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"e-commerce\".tbl_customer");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                Customer customer = new Customer(id, firstName, lastName, address, phone);
                customerList.add(customer);
            }
            return customerList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Customer customer) {
        //TODO
        deleteById(customer.id());
        add(customer);
    }


    @Override
    public void deleteById(int customerId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"e-commerce\".tbl_customer WHERE id = ?");
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
