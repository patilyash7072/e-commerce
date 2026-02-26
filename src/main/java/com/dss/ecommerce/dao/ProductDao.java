package com.dss.ecommerce.dao;

import com.dss.ecommerce.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public interface ProductDao {

    //    1. Get all products
    public List<Product> getAll();

    public Map<Product, Integer> getAllWithQuantity();

//    2. Add product
    public void add(Product product);

//    3. Update product
    public void update(Product product);

//    4. Delete product
    public void deleteById(int id);

    public Product getById(Integer productId);


}
