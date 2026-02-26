package com.dss.ecommerce.service;

import com.dss.ecommerce.dao.CustomerDao;
import com.dss.ecommerce.entity.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAll(){
        return customerDao.getAll();
    }

    public void add(Customer customer){
        customerDao.add(customer);
    }

    public void update(Customer customer) {
        customerDao.update(customer);
    }

    public void deleteById(int customerId){
        customerDao.deleteById(customerId);
    }


}
