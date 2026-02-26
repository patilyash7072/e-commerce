package com.dss.ecommerce.frontend;

import com.dss.ecommerce.credential.PaymentCredential;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.enums.PaymentMode;
import com.dss.ecommerce.exception.*;
import com.dss.ecommerce.service.CartService;
import com.dss.ecommerce.service.CheckoutService;
import com.dss.ecommerce.service.ProductService;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerMenu {
    protected static int displayMenuAndGetChoice(Scanner sc) {
        int input;
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Select any option");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("1. SHOW ALL PRODUCTS");
        System.out.println("2. REFRESH ALL PRODUCTS");
        System.out.println("3. ADD PRODUCT TO CART");
        System.out.println("4. REMOVE PRODUCT FROM CART");
        System.out.println("5. DISPLAY CART");
        System.out.println("6. RESET CART");
        System.out.println("7. ADD PAYMENT CREDENTIALS AND CHECKOUT");
        System.out.println("0. EXIT PROGRAM");
        System.out.print("Enter your choice: ");
        input = sc.nextInt();
        System.out.println("---------------------------------------------------------------------------------");
        return input;
    }

    protected static void applicationMenu(ProductService productService, CartService cartService, Cart cart,
                                          CheckoutService checkoutService)
            throws InvalidQuantityException, ProductNotFoundException, InsufficientStockException,
            PaymentFailedException, SQLException, EmptyCartException, UserNotFoundException {

        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("WELCOME TO E-COMMERCE APPLICATION");

        do {
            choice = CustomerMenu.displayMenuAndGetChoice(sc);

            switch (choice) {
                case 1 -> PrintTable.printAllProducts(productService.getAll());
                case 2 -> productService.refreshProductList();
                case 3 -> {
                    int productId = ProductUtil.getId();
                    int quantity = ProductUtil.getQuantity();
                    cartService.addToCart(productId, quantity);
                }
                case 4 -> {
                    int productId = ProductUtil.getId();
                    int quantity = ProductUtil.getQuantity();
                    cartService.removeFromCart(productId, quantity);
                }
                case 5 -> PrintTable.printCart(cart);
                case 6 -> cartService.resetCart();
                case 7 -> {
                    PaymentMode mode = CustomerMenu.getPaymentMode();
                    PaymentCredential credential = Credential.getCredential(mode);
                    checkoutService.checkout(cart, mode, credential);
                }
            }
        } while (choice > 0 && choice < 8);
    }

    protected static PaymentMode getPaymentMode() {
        Scanner sc = new Scanner(System.in);
        int input;
        PaymentMode mode = null;
        do {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("Select PAYMENT MODE");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("1. COD");
            System.out.println("2. UPI");
            System.out.println("3. DEBIT CARD");
            System.out.println("4. CREDIT CARD");
            System.out.println("5. NET BANKING");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            input = sc.nextInt();
            System.out.println("---------------------------------------------------------------------------------");
            if (input > 0 && input < 6) {
                mode = PaymentMode.values()[input - 1];
            }
        } while (input < 1 || input > 5);
        return mode;
    }

}
