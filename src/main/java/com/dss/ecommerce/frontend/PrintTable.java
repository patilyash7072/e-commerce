package com.dss.ecommerce.frontend;

import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Customer;
import com.dss.ecommerce.entity.Product;

import java.util.List;
import java.util.Map;

public class PrintTable {
    public static void printCart(Cart cart) {
        System.out.printf("%-5s | %-20s | %-20s | %-15s | %-20s%n", "ID", "Name", "Company", "Price", "Quantity");
        System.out.println("---------------------------------------------------------------------------------");

        cart.getCartAsMap().forEach(
                (product, quantity) -> {
                    System.out.printf("%-5d | %-20s | %-20s | %-15d | %-20d%n",
                            product.productId(),
                            product.name(),
                            product.company(),
                            product.price(),
                            quantity);
                }

        );

        System.out.println("Total Bill : " + cart.getBill());

    }

    public static void printAllProducts(List<Product> products) {
        System.out.printf("%-5s | %-20s | %-20s| %-20s%n", "ID", "Name", "Company", "Price");
        System.out.println("---------------------------------------------------------------------------------");

        products.forEach(
                (product) -> {
                    System.out.printf("%-5d | %-20s | %-20s | %-20d%n",
                            product.productId(),
                            product.name(),
                            product.company(),
                            product.price());
                }

        );

    }

    public static void printAllProductsWithQuantity(Map<Product, Integer> productMappedWithQuantity) {
        System.out.printf("%-5s | %-20s | %-20s| %-10s| %-10s%n", "ID", "Name", "Company", "Price", "Quantity");
        System.out.println("---------------------------------------------------------------------------------");

        productMappedWithQuantity.forEach(
                (product, quantity) -> {
                    System.out.printf("%-5d | %-20s | %-20s | %-10s | %-10d%n",
                            product.productId(),
                            product.name(),
                            product.company(),
                            product.price(),
                            quantity);
                }

        );

    }

    public static void printAllCustomers(List<Customer> customers) {
        System.out.printf("%-5s | %-10s | %-10s| | %-10s| %-10s%n", "ID", "First Name", "Last Name", "Address", "Phone");
        System.out.println("---------------------------------------------------------------------------------");

        customers.forEach(
                (customer) -> {
                    System.out.printf("%-5d | %-10s | %-10s | %-10s | %-10s%n",
                            customer.id(),
                            customer.firstName(),
                            customer.lastname(),
                            customer.address(),
                            customer.phone());
                }

        );
    }
}
