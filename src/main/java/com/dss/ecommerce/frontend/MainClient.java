package com.dss.ecommerce.frontend;

import com.dss.ecommerce.credential.LoginCredential;
import com.dss.ecommerce.dao.*;
import com.dss.ecommerce.daoImpl.*;
import com.dss.ecommerce.enums.UserRole;
import com.dss.ecommerce.exception.*;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.external.LoginSimulator;
import com.dss.ecommerce.external.ThirdPartyPaymentSimulator;
import com.dss.ecommerce.service.*;

import java.sql.Connection;
import java.sql.SQLException;

public class MainClient {

    public void startUI() throws SQLException, InsufficientStockException, InvalidQuantityException, ProductNotFoundException, PaymentFailedException, EmptyCartException, InvalidLoginCredential, UserNotFoundException {
        try(Connection connection = DbService.getConnection()) {
            ProductDao productDao = new ProductDaoImpl(connection);
            InventoryDao inventoryDao = new InventoryDaoImpl(connection);
            OrderDao orderDao = new OrderDaoImpl(connection);
            PaymentDao paymentDao = new PaymentDaoImpl(connection);
            CustomerDao customerDao = new CustomerDaoImpl(connection);

            Cart cart = new Cart();
            ThirdPartyPaymentSimulator simulator = new ThirdPartyPaymentSimulator();

            ProductService productService = new ProductService(productDao);
            CartService cartService = new CartService(cart, productService);
            InventoryService inventoryService = new InventoryService(inventoryDao);
            OrderService orderService = new OrderService(orderDao);
            PaymentService paymentService = new PaymentService(simulator, paymentDao);
            CustomerService customerService = new CustomerService(customerDao);

            CheckoutService checkoutService = new CheckoutService(paymentService, inventoryService, orderService);

            LoginSimulator loginSimulator = new LoginSimulator();
            LoginService loginService = new LoginService(loginSimulator, customerService);

            WelcomeMenu.showLoginMenuOrShowSimulations(loginService, productService, inventoryService, customerService, cartService, checkoutService);

        }
    }







}
