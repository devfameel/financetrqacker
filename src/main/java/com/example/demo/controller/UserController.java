package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    private UserService userService;

    // register user
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()
                || user.getEmail() == null || user.getEmail().trim().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            return "Username, email and password are required";
        }

        boolean usernameExists = userService.usernameExists(user.getUsername());

        if (usernameExists) {
            return "Email already exists";
        }

        boolean emailExists = userService.emailExists(user.getEmail());

        if (emailExists) {
            return "Email already exists";
        }

        userService.register(user);
        return "User registered successfully";
    }

    // login user
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            return "Username and password are required";
        }

        return userService.login(user.getUsername(), user.getPassword());
    }
}
