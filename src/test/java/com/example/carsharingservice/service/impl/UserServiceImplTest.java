package com.example.carsharingservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

class UserServiceImplTest {
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void addUser_ok() {
        User userToAdd = new User();
        userToAdd.setId(1L);
        userToAdd.setFirstName("John");
        userToAdd.setLastName("Doe");
        userToAdd.setEmail("johndoe@gmail.com");
        userToAdd.setPassword("johndoe1234");
        userToAdd.setRole(User.Role.CUSTOMER);

        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(userToAdd.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(userToAdd)).thenReturn(userToAdd);

        User savedUser = userService.add(userToAdd);
        assertNotNull(savedUser);
        assertEquals(userToAdd.getId(), savedUser.getId());
        assertEquals(userToAdd.getFirstName(), savedUser.getFirstName());
        assertEquals(userToAdd.getLastName(), savedUser.getLastName());
        assertEquals(userToAdd.getEmail(), savedUser.getEmail());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals(userToAdd.getRole(), savedUser.getRole());

        verify(passwordEncoder, times(1)).encode("johndoe1234");
        verify(userRepository, times(1)).save(userToAdd);
    }

    @Test
    public void getUserById_ok() {
        Long userId = 1L;
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("johndoe1234");
        user.setRole(User.Role.CUSTOMER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User actual = userService.get(userId);
        assertNotNull(actual);
        assertEquals(user.getId(), actual.getId());
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getLastName(), actual.getLastName());
        assertEquals(user.getEmail(), actual.getEmail());
        assertEquals(user.getPassword(), actual.getPassword());
        assertEquals(user.getRole(), actual.getRole());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void getUserByIdNotFound_notOk() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.get(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testUpdateUser() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setFirstName("John");
        userToUpdate.setLastName("Doe");
        userToUpdate.setEmail("johndoe@gmail.com");
        userToUpdate.setPassword("johndoe1234");
        userToUpdate.setRole(User.Role.CUSTOMER);
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        User updatedUser = userService.update(userToUpdate);
        assertNotNull(updatedUser);
        assertEquals(userToUpdate.getId(), updatedUser.getId());
        assertEquals(userToUpdate.getFirstName(), updatedUser.getFirstName());
        assertEquals(userToUpdate.getLastName(), updatedUser.getLastName());
        assertEquals(userToUpdate.getEmail(), updatedUser.getEmail());
        assertEquals(userToUpdate.getPassword(), updatedUser.getPassword());
        assertEquals(userToUpdate.getRole(), updatedUser.getRole());

        verify(userRepository, times(1)).save(userToUpdate);
    }

    @Test
    public void updateUserRole_ok() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("johndoe@gmail.com");
        existingUser.setPassword("johndoe1234");
        existingUser.setRole(User.Role.CUSTOMER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User.Role newRole = User.Role.MANAGER;
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");
        updatedUser.setEmail("johndoe@gmail.com");
        updatedUser.setPassword("johndoe1234");
        updatedUser.setRole(newRole);
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.update(userId, newRole);
        assertNotNull(result);
        assertEquals(existingUser.getId(), result.getId());
        assertEquals(existingUser.getFirstName(), result.getFirstName());
        assertEquals(existingUser.getLastName(), result.getLastName());
        assertEquals(existingUser.getEmail(), result.getEmail());
        assertEquals(existingUser.getPassword(), result.getPassword());
        assertEquals(newRole, result.getRole());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void findByEmail_ok() {
        String email = "johndoe@gmail.com";
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("johndoe1234");
        user.setRole(User.Role.CUSTOMER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByEmail(email);
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void findByEmailNotFound_notOk() {
        String email = "nonexistent@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByEmail(email);
        assertFalse(foundUser.isPresent());

        verify(userRepository, times(1)).findByEmail(email);
    }
}