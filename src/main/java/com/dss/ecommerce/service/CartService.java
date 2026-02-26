package com.dss.ecommerce.service;

import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.exception.InvalidQuantityException;
import com.dss.ecommerce.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CartService {

    private Cart cart;
    private ProductService productService;
    private Logger logger;

    public CartService(Cart cart, ProductService productService) {
        this.cart = cart;
        this.productService = productService;
        this.logger = LoggerFactory.getLogger(CartService.class);
    }

    //1. Add product to cart
    public void addToCart(Integer productId, int quantity) throws InvalidQuantityException, ProductNotFoundException {
        Product product = productService.getById(productId);
        cart.addToCart(product, quantity);
        logger.info("Product product id: {} added successfully with quantity: {}", productId , quantity);
    }

    //2. Remove product from cart
    public void removeFromCart(Integer productId, int quantity) throws InvalidQuantityException, ProductNotFoundException {
        Product product = productService.getById(productId);
        cart.removeFromCart(product, quantity);
        logger.info("Product product id: {} removed successfully with quantity: {}", productId , quantity);
    }

    //4. Get product Quantity
    public Integer getProductQuantity(Integer productId) throws ProductNotFoundException {
        Product product = productService.getById(productId);
        return cart.getProductQuantity(product);
    }

    //5. Get cart
    public Cart getCart() {
        return cart;
    }

    //6. Get bill
    public int getBill() {
        return cart.getBill();
    }

    //7. Reset cart
    public void resetCart() {
        cart.resetCart();
        logger.info("Cart reset successfully");
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}
