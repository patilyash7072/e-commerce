package com.dss.ecommerce.dao;

import com.dss.ecommerce.entity.Customer;

import java.util.List;

public interface CustomerDao {
    public void add(Customer customer);

    public List<Customer> getAll();

    public void update(Customer customer);

    public void deleteById(int customerId);
}
