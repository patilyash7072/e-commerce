package com.dss.ecommerce.daoImpl;

import com.dss.ecommerce.dao.ProductDao;
import com.dss.ecommerce.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductDaoImpl implements ProductDao {

    Connection connection;

    public ProductDaoImpl(Connection connection) {
        this.connection = connection;
    }

    //    1. Get all products
    public List<Product> getAll() {
        List<Product> productSet = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM \"e-commerce\".tbl_product";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String company = resultSet.getString("company");
                int price = resultSet.getInt("price");
                Product product = new Product(id, name, company, price);
                productSet.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productSet;
    }

    public Map<Product, Integer> getAllWithQuantity() {
        Map<Product, Integer> productMappedWithQuantity = new TreeMap<>();

        try {
            Statement statement = connection.createStatement();
            String query = """
                            SELECT p.id, p.name, p.company, p.price, i.quantity_available 
                            FROM "e-commerce".tbl_product p 
                            LEFT JOIN "e-commerce".tbl_inventory i 
                            ON p.id = i.product_id
                            """;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String company = resultSet.getString("company");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity_available");

                Product product = new Product(id, name, company, price);

                productMappedWithQuantity.put(product, quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productMappedWithQuantity;
    }

//    2. Add product
    public void add(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    INSERT INTO \"e-commerce\".tbl_product (id, name, company, price)
                    VALUES (?, ?, ?, ?)
                    """);

            preparedStatement.setInt(1, product.productId());
            preparedStatement.setString(2, product.name());
            preparedStatement.setString(3, product.company());
            preparedStatement.setInt(4, product.price());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    3. Update product
    public void update(Product product) {
        //TODO
        deleteById(product.productId());
        add(product);
    }

//    4. Delete product
    public void deleteById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    DELETE FROM "e-commerce".tbl_product 
                    WHERE id = ?
                    """);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getById(Integer productId) {
        try {
            Product product = null;
            String query = "SELECT * FROM \"e-commerce\".tbl_product WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String company = resultSet.getString("company");
                int price = resultSet.getInt("price");
                product = new Product(id, name, company, price);
            }

            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
