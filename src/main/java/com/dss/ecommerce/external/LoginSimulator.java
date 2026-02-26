package com.dss.ecommerce.external;

import com.dss.ecommerce.credential.LoginCredential;
import com.dss.ecommerce.entity.Customer;
import com.dss.ecommerce.enums.UserRole;
import com.dss.ecommerce.exception.InvalidLoginCredential;
import com.dss.ecommerce.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginSimulator {

    private static Map<LoginCredential, String> loginCredentialMappedWithUserId = new HashMap<>();
    private static Map<Thread, String> threadMappedWihUserId = new HashMap<>();

    public static void initiateLoginSimulator(List<Customer> customers) {
        LoginCredential adminCredential = new LoginCredential("admin", "admin", UserRole.ADMIN);
        loginCredentialMappedWithUserId.put(adminCredential, "0");

        for (int i = 1; i <= customers.size(); i++) {
            Customer customer = customers.get(i - 1);
            String firstName = customer.firstName().toLowerCase();
            LoginCredential loginCredential = new LoginCredential(firstName, firstName, UserRole.CUSTOMER);
            loginCredentialMappedWithUserId.put(loginCredential, Integer.toString(i));
        }
    }


    public void login(LoginCredential loginCredential) throws InvalidLoginCredential {
        checkLoginCredentials(loginCredential);
        String userId = loginCredentialMappedWithUserId.get(loginCredential);
        threadMappedWihUserId.put(Thread.currentThread(), userId);
        Thread.currentThread().setName(userId.toLowerCase());
    }

    private void checkLoginCredentials(LoginCredential loginCredential) throws InvalidLoginCredential {
        boolean isLoginAlreadyExist = loginCredentialMappedWithUserId.containsKey(loginCredential);
        if (!isLoginAlreadyExist) {
            throw new InvalidLoginCredential("Please provide correct username, password and role");
        }
    }

    public static String getUserId() throws UserNotFoundException {
        Thread thread = Thread.currentThread();
        boolean isThreadRegistered = threadMappedWihUserId.containsKey(thread);
        if (!isThreadRegistered) {
            throw new UserNotFoundException("User was not found");
        }
        return threadMappedWihUserId.get(thread);
    }
}
