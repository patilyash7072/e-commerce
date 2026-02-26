package com.dss.ecommerce.simulation;

import com.dss.ecommerce.credential.CardCredential;
import com.dss.ecommerce.credential.LoginCredential;
import com.dss.ecommerce.dao.*;
import com.dss.ecommerce.daoImpl.*;
import com.dss.ecommerce.entity.Cart;
import com.dss.ecommerce.entity.Product;
import com.dss.ecommerce.enums.PaymentMode;
import com.dss.ecommerce.enums.UserRole;
import com.dss.ecommerce.exception.*;
import com.dss.ecommerce.external.LoginSimulator;
import com.dss.ecommerce.external.ThirdPartyPaymentSimulator;
import com.dss.ecommerce.frontend.PrintTable;
import com.dss.ecommerce.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SingleThreadSimulation {
    static Logger logger;

    public static void start() throws SQLException {
        logger = LoggerFactory.getLogger(SingleThreadSimulation.class);

        LoginCredential threadOneLogin = new LoginCredential("aamir", "aamir" , UserRole.CUSTOMER);
        Thread t1 = new Thread(task(threadOneLogin));
        t1.start();

    }

    static Runnable task(LoginCredential loginCredential) throws SQLException {


        return () -> {

            try (Connection connection = DbService.getConnection()) {
                ProductDao productDao = new ProductDaoImpl(connection);
                ProductService productService = new ProductService(productDao);

                CustomerDao customerDao = new CustomerDaoImpl(connection);
                CustomerService customerService = new CustomerService(customerDao);

                LoginSimulator loginSimulator = new LoginSimulator();
                LoginService loginService = new LoginService(loginSimulator, customerService);

                loginService.login(loginCredential);

                logger.info("Started Thread {}", Thread.currentThread().getName());


                List<Product> products = productService.getAll();
                Collections.sort(products);

                Cart cart = new Cart();
                CartService cartService = new CartService(cart, productService);

                logger.info("Initiating adding multiple products to Cart");

                products.forEach(
                        product -> {
                            try {
                                try {
                                    cartService.addToCart(product.productId(), 1);
                                } catch (ProductNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            } catch (InvalidQuantityException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );


                PrintTable.printCart(cartService.getCart());
                System.out.println("Bill = " + cartService.getBill());



                Product product = products.get(0);
                Integer productQuantity = cartService.getProductQuantity(product.productId());
                System.out.println("product quantity "+ productQuantity);
                cartService.addToCart(product.productId(),4);

                Integer newProductQuantity = cartService.getProductQuantity(product.productId());
                System.out.println("product quantity "+ newProductQuantity);
                cartService.removeFromCart(product.productId(), 3);

                Integer newestProductQuantity = cartService.getProductQuantity(product.productId());
                System.out.println("product quantity "+ newestProductQuantity);
                System.out.println("Bill= " + cartService.getBill());





                ThirdPartyPaymentSimulator paymentSimulator = new ThirdPartyPaymentSimulator();
                PaymentDao paymentDao = new PaymentDaoImpl(connection);
                OrderDao orderDao = new OrderDaoImpl(connection);
                InventoryDao inventoryDao = new InventoryDaoImpl(connection);
                PaymentService paymentService = new PaymentService(paymentSimulator, paymentDao);
                OrderService orderService = new OrderService(orderDao);
                InventoryService inventoryService = new InventoryService(inventoryDao);
                CheckoutService checkoutService = new CheckoutService(paymentService, inventoryService, orderService);
                CardCredential credential = new CardCredential("123412341234", 434, LocalDate.now());

                checkoutService.checkout(cart, PaymentMode.CREDIT_CARD, credential);




            } catch (ProductNotFoundException | InvalidQuantityException | InsufficientStockException |
                     PaymentFailedException | SQLException | EmptyCartException | UserNotFoundException |
                     InvalidLoginCredential e) {
                throw new RuntimeException(e);
            }
        };
    }
}
