package com.dss.ecommerce.frontend;

import java.util.Scanner;

public class ProductUtil {

    protected static com.dss.ecommerce.entity.Product getProduct() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter PRODUCT ID: ");
        int productId = sc.nextInt();
        System.out.print("Enter PRODUCT NAME: ");
        String name = sc.next();
        System.out.print("Enter COMPANY: ");
        String company = sc.next();
        System.out.print("Enter PRICE: ");
        int price = sc.nextInt();

        return new com.dss.ecommerce.entity.Product(productId, name, company, price);
    }

    protected static int getId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID: ");
        return sc.nextInt();
    }

    protected static int getQuantity() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter QUANTITY: ");
        return sc.nextInt();
    }
}
