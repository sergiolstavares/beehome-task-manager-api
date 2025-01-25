package com.beehome.taskmanagerapi.controller;

import com.beehome.taskmanagerapi.dto.AuthRequest;
import com.beehome.taskmanagerapi.dto.UserRequest;
import com.beehome.taskmanagerapi.model.UserModel;
import com.beehome.taskmanagerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.beehome.taskmanagerapi.util.ErrorUtil.createErrorResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user) {
        try {
            UserModel response = userService.createUser(user);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest credentials) {
        try {
            Boolean response = userService.login(credentials);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
