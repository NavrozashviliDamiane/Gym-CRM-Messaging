package com.epam.crmgym.config;

import com.epam.crmgym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class SecurityBeansInjectorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityBeansInjector securityBeansInjector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void testAuthenticationProvider() {
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        AuthenticationProvider authenticationProvider = securityBeansInjector.authenticationProvider(userDetailsService, passwordEncoder);
        assertNotNull(authenticationProvider);
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityBeansInjector.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    @Test
    void testUserDetailsService() {
        UserDetailsService userDetailsService = securityBeansInjector.userDetailsService();
        assertNotNull(userDetailsService);
    }
}
