package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // register user
    public User register(User user) {
        user.setUsername(user.getUsername().trim());
        user.setEmail(user.getEmail().trim().toLowerCase());
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    // check username already exists ,This will check if username already exists before registration.
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username.trim());
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    // login user
    public String login(String username, String password) {

        User user = userRepository.findByUsername(username.trim());

        if (user == null) {
            return "Username not found. Please sign up first.";
        }

        String encodedPassword = encodePassword(password);

        if (encodedPassword.equals(user.getPassword())) {
            return "Login successful";
        }

        if (password.equals(user.getPassword())) {
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return "Login successful";
        }

        return "Invalid password";
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Password encoding is not available", ex);
        }
    }
}
