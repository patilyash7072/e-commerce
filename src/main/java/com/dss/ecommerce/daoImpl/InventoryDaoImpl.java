package com.dss.ecommerce.daoImpl;

import com.dss.ecommerce.dao.InventoryDao;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.exception.InsufficientStockException;
import com.dss.ecommerce.exception.InvalidQuantityException;
import com.dss.ecommerce.exception.ProductNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class InventoryDaoImpl implements InventoryDao {

    private final Connection connection;

    public InventoryDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public void getAndUpdateAll(Cart cart) throws ProductNotFoundException, InsufficientStockException {
        try {
            connection.setAutoCommit(false);
            for (Map.Entry<Product, Integer> entry : cart.getCartAsMap().entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();
                int availableQuantity = getAvailableQuantityAndLockRow(product);
                if (availableQuantity < quantity) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    throw new InsufficientStockException("Stock is insufficient for product: " + product);
                }
            }
            for (Map.Entry<Product, Integer> entry : cart.getCartAsMap().entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();
                bookProductWithQuantity(product.productId(), quantity);
            }
            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getAvailableQuantityAndLockRow(Product product) throws ProductNotFoundException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT quantity_available 
                    FROM "e-commerce".tbl_inventory 
                    WHERE product_id = ? 
                    FOR UPDATE;
                    """);
            preparedStatement.setInt(1, product.productId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new ProductNotFoundException("Product productId: "+ product.productId() + " was not found in inventory");
            }
            return resultSet.getInt("quantity_available");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void bookProductWithQuantity(int productId, int quantity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE "e-commerce".tbl_inventory 
                    SET quantity_available = quantity_available - ? 
                    WHERE product_id = ?
                    """);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateQuantity(int productId, int quantity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE "e-commerce".tbl_inventory 
                    SET quantity_available = ? 
                    WHERE product_id = ?
                    """);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addProduct(Product product, int quantity) throws InvalidQuantityException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    INSERT INTO "e-commerce".tbl_inventory(product_id, quantity_available) 
                    VALUES (?, ?);
                    """);
            if (quantity < 0) {
                throw new InvalidQuantityException("Quantity should be greater than or equal to 0");
            }
            preparedStatement.setInt(1, product.productId());
            preparedStatement.setInt(2, quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteProductById(int productId) throws ProductNotFoundException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"e-commerce\".tbl_inventory WHERE id = ?");
            preparedStatement.setInt(1, productId);
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new ProductNotFoundException("Product was not found in inventory");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
