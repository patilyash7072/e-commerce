package com.dss.ecommerce.frontend;

import com.dss.ecommerce.entity.Customer;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.exception.InvalidQuantityException;
import com.dss.ecommerce.exception.ProductNotFoundException;
import com.dss.ecommerce.service.CustomerService;
import com.dss.ecommerce.service.InventoryService;
import com.dss.ecommerce.service.ProductService;

import java.util.Scanner;

public class AdminMenu {

    protected static int displayMenuAndGetChoice(Scanner sc) {
        int input;
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Select any option");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("PRODUCT");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("1. VIEW ALL PRODUCTS");
        System.out.println("2. ADD NEW PRODUCT");
        System.out.println("3. UPDATE PRODUCT");
        System.out.println("4. UPDATE PRODUCT QUANTITY");
        System.out.println("5. DELETE PRODUCT");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("CUSTOMER");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("6. VIEW ALL CUSTOMERS");
        System.out.println("7. ADD NEW CUSTOMER");
        System.out.println("8. UPDATE CUSTOMER");
        System.out.println("9. DELETE CUSTOMER");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.print("Enter your choice: ");
        input = sc.nextInt();
        System.out.println("---------------------------------------------------------------------------------");
        return input;
    }

    protected static void applicationMenu
            (ProductService productService, InventoryService inventoryService, CustomerService customerService)
            throws ProductNotFoundException, InvalidQuantityException {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("WELCOME TO E-COMMERCE APPLICATION ADMIN CONSOLE");
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            choice = AdminMenu.displayMenuAndGetChoice(sc);

            switch (choice) {
                case 1 -> PrintTable.printAllProductsWithQuantity(productService.getAllWithQuantity());
                case 2 -> {
                    Product product = ProductUtil.getProduct();
                    int quantity = ProductUtil.getQuantity();
                    productService.add(product);
                    inventoryService.addProduct(product, quantity);
                }
                case 3 -> {
                    Product product = ProductUtil.getProduct();
                    productService.update(product);
                }
                case 4 -> {
                    int productId = ProductUtil.getId();
                    int quantity = ProductUtil.getQuantity();
                    inventoryService.updateQuantity(productId, quantity);
                }
                case 5 -> {
                    int productId = ProductUtil.getId();
                    productService.deleteById(productId);
                    inventoryService.deleteProductById(productId);
                }
                case 6 -> PrintTable.printAllCustomers(customerService.getAll());
                case 7 -> {
                    Customer customer = CustomerUtil.getCustomer();
                    customerService.add(customer);
                }
                case 8 -> {
                    Customer customer = CustomerUtil.getCustomer();
                    customerService.update(customer);
                }
                case 9 -> {
                    int id = CustomerUtil.getId();
                    customerService.deleteById(id);
                }
            }
        } while (choice > 0 && choice < 10);
    }

}
