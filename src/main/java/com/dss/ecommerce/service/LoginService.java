package com.dss.ecommerce.service;

import com.dss.ecommerce.credential.LoginCredential;
import com.dss.ecommerce.entity.Customer;
import com.dss.ecommerce.enums.UserRole;
import com.dss.ecommerce.exception.InvalidLoginCredential;
import com.dss.ecommerce.exception.UserNotFoundException;
import com.dss.ecommerce.external.LoginSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoginService {
    private final LoginSimulator loginSimulator;
    private final CustomerService customerService;
    private Logger logger;

    public LoginService(LoginSimulator loginSimulator, CustomerService customerService) {
        this.logger = LoggerFactory.getLogger(LoginService.class);
        logger.info("Initiating login service");
        this.loginSimulator = loginSimulator;
        this.customerService = customerService;
        List<Customer> customers = customerService.getAll();
        LoginSimulator.initiateLoginSimulator(customers);
        logger.info("Initiated login service");
    }

    //    1. log in user using loginCredential
    public void login(LoginCredential loginCredential) throws InvalidLoginCredential {
        loginSimulator.login(loginCredential);
        logger.info("Logged in as " + loginCredential.username());
    }

    public static String getUserId() throws UserNotFoundException {
        return LoginSimulator.getUserId();
    }
}
