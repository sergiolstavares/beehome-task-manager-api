package com.beehome.taskmanagerapi.service;

import com.beehome.taskmanagerapi.dto.AuthRequest;
import com.beehome.taskmanagerapi.dto.UserRequest;
import com.beehome.taskmanagerapi.model.UserModel;
import com.beehome.taskmanagerapi.repository.UserRepository;
import com.beehome.taskmanagerapi.util.CryptoUtil;
import com.beehome.taskmanagerapi.validate.UserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidate userValidate;

    @Autowired
    CryptoUtil cryptoUtil;

    public UserModel createUser(UserRequest user) {
        userValidate.createUserValidate(user);

        String hashedPassword = cryptoUtil.hashPassword(user.getPassword());
        UserModel userCreated = new UserModel(user.getUsername(), user.getEmail(), hashedPassword);

        return userRepository.save(userCreated);
    }
}
