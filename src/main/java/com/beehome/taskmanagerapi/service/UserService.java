package com.beehome.taskmanagerapi.service;

import com.beehome.taskmanagerapi.dto.AuthRequest;
import com.beehome.taskmanagerapi.dto.UserRequest;
import com.beehome.taskmanagerapi.model.UserModel;
import com.beehome.taskmanagerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserModel createUser(UserRequest user) {
        UserModel userCreated = new UserModel(user.getUsername(), user.getEmail(), user.getPassword());

        return userRepository.save(userCreated);
    }

    public Boolean authenticateUser(AuthRequest credentials) {
        Optional<UserModel> userIsValid = userRepository.findUserByEmailAndPassword(credentials.getEmail(), credentials.getPassword());

        return userIsValid.isPresent();
    }
}
