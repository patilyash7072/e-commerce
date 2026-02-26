package com.dss.ecommerce.frontend;

import com.dss.ecommerce.credential.LoginCredential;
import com.dss.ecommerce.credential.PaymentCredential;
import com.dss.ecommerce.enums.PaymentMode;
import com.dss.ecommerce.enums.UserRole;
import com.dss.ecommerce.exception.*;
import com.dss.ecommerce.service.*;
import com.dss.ecommerce.simulation.MultiThreadingSimulation;
import com.dss.ecommerce.simulation.SingleThreadSimulation;

import java.sql.SQLException;
import java.util.Scanner;

public class WelcomeMenu {
    private static int displayMenuAndGetChoice() {
        Scanner sc = new Scanner(System.in);
        int input;
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("Select any option");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Start SingleThread Simulation");
            System.out.println("3. Start MultiThreading Simulation");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            input = sc.nextInt();
            System.out.println("---------------------------------------------------------------------------------");;
        return input;
    }

    protected static void showLoginMenuOrShowSimulations
            (LoginService loginService, ProductService productService, InventoryService inventoryService, CustomerService customerService, CartService cartService, CheckoutService checkoutService) throws InvalidLoginCredential, SQLException, InvalidQuantityException, ProductNotFoundException, EmptyCartException, UserNotFoundException, InsufficientStockException, PaymentFailedException {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("WELCOME TO E-COMMERCE APPLICATION");

        int choice;
        do {
            choice = displayMenuAndGetChoice();

            switch (choice) {
                case 1 -> {
                    LoginCredential loginCredential = Login.getLoginCredential();
                    loginService.login(loginCredential);
                    if (loginCredential.userRole().equals(UserRole.ADMIN)) {
                        AdminMenu.applicationMenu(productService, inventoryService, customerService);
                    } else {
                        CustomerMenu.applicationMenu(productService, cartService , cartService.getCart(), checkoutService);
                    }
                }
                case 2 -> {
                    SingleThreadSimulation.start();
                    choice = Input.autoExit();
                }
                case 3 -> {
                    MultiThreadingSimulation.start();
                    choice = Input.autoExit();
                }
            }
        } while (choice > 0 && choice < 4);
    }
}
