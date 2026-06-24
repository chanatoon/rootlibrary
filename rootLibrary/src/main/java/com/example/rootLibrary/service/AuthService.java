package com.example.rootLibrary.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${app.auth.employee-id}")
    private String validEmployeeId;

    @Value("${app.auth.password}")
    private String validPassword; 

    public boolean login(String employeeId, String password) {
        return validEmployeeId.equals(employeeId) && validPassword.equals(password);
    }
}
