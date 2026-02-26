package com.dss.ecommerce;

import com.dss.ecommerce.exception.*;
import com.dss.ecommerce.frontend.MainClient;

import java.sql.SQLException;

public class Main {
    static void main() throws SQLException, InsufficientStockException, InvalidQuantityException, ProductNotFoundException, PaymentFailedException, EmptyCartException, InvalidLoginCredential, UserNotFoundException {
        MainClient client = new MainClient();
        client.startUI();
    }
}
