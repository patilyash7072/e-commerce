package com.dss.ecommerce.entity;

import com.dss.ecommerce.exception.InvalidQuantityException;
import com.dss.ecommerce.exception.ProductNotFoundException;


import java.util.Map;
import java.util.TreeMap;

public class Cart {
    Map<Product, Integer> cart = new TreeMap<>();

    public void addToCart(Product product, int quantity) throws InvalidQuantityException, ProductNotFoundException {
        if (quantity < 1) {
            throw new InvalidQuantityException("Quantity should be greater than 1");
        }
        if (cart.containsKey(product)) {
            cart.put(product, getProductQuantity(product) + quantity);
        } else {
            cart.put(product, quantity);
        }
    }

    public void removeFromCart(Product product, int quantity) throws InvalidQuantityException, ProductNotFoundException {
        checkProductInCart(product);
        if (quantity == cart.get(product)) {
            cart.remove(product);
        } else if (quantity > cart.get(product)) {
            throw new InvalidQuantityException("Quantity should be less than available quantity");
        }
        cart.put(product, getProductQuantity(product) - quantity);
    }

    private void checkProductInCart(Product product) throws ProductNotFoundException {
        if (!cart.containsKey(product)) {
            throw new ProductNotFoundException("Product with product id: " + product.productId() + " was not found in cart");
        }
    }

    public Integer getProductQuantity(Product product) throws ProductNotFoundException {
        checkProductInCart(product);
        Integer productQuantity = cart.get(product);
        if (productQuantity == null) {
            productQuantity = 0;
        }
        return productQuantity;
    }

    public Map<Product, Integer> getCartAsMap() {
        return cart;
    }

    public int getBill() {
        return cart.entrySet()
                .stream()
                .mapToInt(
                        (productEntry) -> productEntry.getKey().price() * productEntry.getValue())
                .sum();
    }

    public void resetCart() {
        cart.clear();
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }
}
