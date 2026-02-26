package com.dss.ecommerce.frontend;

import com.dss.ecommerce.credential.LoginCredential;
import com.dss.ecommerce.enums.UserRole;

import java.util.Scanner;

public class Login {

    protected static LoginCredential getLoginCredential() {
        Scanner sc = new Scanner(System.in);

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Login to ECOMMERCE APPLICATION");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.print("Enter USERNAME: ");
        String username = sc.next();
        System.out.print("Enter PASSWORD: ");
        String password = sc.next();
        UserRole userRole = getUserRole();

        return new LoginCredential(username, password, userRole);
    }

    private static UserRole getUserRole() {
        Scanner sc = new Scanner(System.in);
        int input;
        UserRole userRole = null;
        do {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("Select USER ROLE");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("1. ADMIN");
            System.out.println("2. CUSTOMER");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            input = sc.nextInt();
            System.out.println("---------------------------------------------------------------------------------");
            if (input > 0 && input < 3) {
                userRole = UserRole.values()[input - 1];
            }
        } while (input < 1 || input > 3);
        return userRole;
    }
}
