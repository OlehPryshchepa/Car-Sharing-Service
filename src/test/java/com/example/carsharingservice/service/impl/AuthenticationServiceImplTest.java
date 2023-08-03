package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.AuthenticationService;
import com.example.carsharingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

class AuthenticationServiceImplTest {
    private AuthenticationService authenticationService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        authenticationService = new AuthenticationServiceImpl(userService, passwordEncoder);
    }

    @Test
    public void register_ok() {
        String email = "john.doe@gmail.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "secretpassword";

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setRole(User.Role.CUSTOMER);

        when(userService.add(any(User.class))).thenReturn(newUser);

        User registeredUser = authenticationService.register(email, firstName, lastName, password);

        assertNotNull(registeredUser);
        assertEquals(email, registeredUser.getEmail());
        assertEquals(firstName, registeredUser.getFirstName());
        assertEquals(lastName, registeredUser.getLastName());
        assertEquals(password, registeredUser.getPassword());
        assertEquals(User.Role.CUSTOMER, registeredUser.getRole());

        verify(userService, times(1)).add(any(User.class));
    }

    @Test
    public void login_ok() throws AuthenticationException {
        String email = "john.doe@gmail.com";
        String password = "secretpassword";
        String hashedPassword = "hashedPassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);

        when(userService.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

        User loggedInUser = authenticationService.login(email, password);
        assertNotNull(loggedInUser);
        assertEquals(email, loggedInUser.getEmail());
        assertEquals(hashedPassword, loggedInUser.getPassword());

        verify(userService, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, hashedPassword);
    }

    @Test
    public void loginInvalidEmail_notOk() {
        String email = "john.doe@gmail.com";
        String password = "secretpassword";

        when(userService.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(AuthenticationException.class, () -> authenticationService.login(email, password));

        verify(userService, times(1)).findByEmail(email);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    public void testLoginInvalidPassword() {
        String email = "john.doe@gmail.com";
        String password = "secretpassword";
        String hashedPassword = "hashedPassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        when(userService.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);
        assertThrows(AuthenticationException.class, () -> authenticationService.login(email, password));

        verify(userService, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, hashedPassword);
    }
}