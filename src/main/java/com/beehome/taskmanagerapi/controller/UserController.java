package com.beehome.taskmanagerapi.controller;

import com.beehome.taskmanagerapi.dto.AuthRequest;
import com.beehome.taskmanagerapi.dto.UserRequest;
import com.beehome.taskmanagerapi.model.UserModel;
import com.beehome.taskmanagerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserModel createUser(@RequestBody UserRequest user) {
        return userService.createUser(user);
    }

    @PostMapping("/auth")
    public boolean authenticateUser(@RequestBody AuthRequest credentials) {
        return userService.authenticateUser(credentials);
    }
}
