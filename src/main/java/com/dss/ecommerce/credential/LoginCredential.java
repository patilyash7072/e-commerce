package com.dss.ecommerce.credential;

import com.dss.ecommerce.enums.UserRole;

public record LoginCredential(String username, String password, UserRole userRole) {
}
