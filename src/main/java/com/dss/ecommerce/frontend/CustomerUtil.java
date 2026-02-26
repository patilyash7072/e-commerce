package com.dss.ecommerce.frontend;

import java.util.Scanner;

public class CustomerUtil {

    protected static com.dss.ecommerce.entity.Customer getCustomer() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter CUSTOMER ID: ");
        int productId = sc.nextInt();
        System.out.print("Enter FIRST NAME: ");
        String firstName = sc.next();
        System.out.print("Enter LAST NAME: ");
        String lastName = sc.next();
        System.out.print("Enter ADDRESS: ");
        String address = sc.next();
        System.out.print("Enter PHONE: ");
        String phone = sc.next();

        return new com.dss.ecommerce.entity.Customer(productId, firstName, lastName, address, phone);
    }

    protected static int getId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID: ");
        return sc.nextInt();
    }

}
