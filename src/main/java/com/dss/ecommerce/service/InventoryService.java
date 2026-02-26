package com.dss.ecommerce.service;

import com.dss.ecommerce.dao.InventoryDao;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.exception.EmptyCartException;
import com.dss.ecommerce.exception.InsufficientStockException;
import com.dss.ecommerce.exception.InvalidQuantityException;
import com.dss.ecommerce.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryService {

    private final InventoryDao inventoryDao;
    private Logger logger;

    public InventoryService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
        this.logger = LoggerFactory.getLogger(InventoryService.class);
    }

    public void checkAndUpdate(Cart cart) throws InsufficientStockException, ProductNotFoundException, EmptyCartException {
        if (cart.isEmpty()) {
            throw new EmptyCartException("No product was found in cart");
        }
        inventoryDao.getAndUpdateAll(cart);
        logger.info("Inventory updated successfully");
    }

    public void addProduct(Product product, int quantity) throws InvalidQuantityException {
        inventoryDao.addProduct(product, quantity);
    }

    public void bookQuantity(int productId, int quantity) {
        inventoryDao.bookProductWithQuantity(productId, quantity);
    }

    public void updateQuantity(int productId, int quantity) {
        inventoryDao.updateQuantity(productId, quantity);
    }

    public void deleteProductById(int productId) throws ProductNotFoundException {
        inventoryDao.deleteProductById(productId);
    }

}
