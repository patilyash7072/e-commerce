package com.dss.ecommerce.dao;

import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.exception.InsufficientStockException;
import com.dss.ecommerce.exception.InvalidQuantityException;
import com.dss.ecommerce.exception.ProductNotFoundException;

public interface InventoryDao {

    public void getAndUpdateAll(Cart cart) throws ProductNotFoundException, InsufficientStockException;

    public void addProduct(Product product, int quantity) throws InvalidQuantityException;

    public void deleteProductById(int productId) throws ProductNotFoundException;

    public void bookProductWithQuantity(int productId, int quantity);

    public void updateQuantity(int productId, int quantity);
}
