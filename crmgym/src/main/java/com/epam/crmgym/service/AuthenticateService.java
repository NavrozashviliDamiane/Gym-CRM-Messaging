package com.epam.crmgym.service;

public interface AuthenticateService {

    boolean matchUserCredentials(String username, String password);
}
