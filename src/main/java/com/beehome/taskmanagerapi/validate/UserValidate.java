package com.beehome.taskmanagerapi.validate;

import com.beehome.taskmanagerapi.dto.UserRequest;
import com.beehome.taskmanagerapi.model.UserModel;
import com.beehome.taskmanagerapi.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidate {

    @Autowired
    UserRepository userRepository;

    public void createUserValidate(UserRequest user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("O email deve ser informado");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ValidationException("O nome deve ser informado");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidationException("A senha deve ser informada");
        }

        Optional<UserModel> emailAlredyInUse = userRepository.findUserByEmail(user.getEmail());
        if (emailAlredyInUse.isPresent()) {
            throw new ValidationException("O email já está em uso");
        }
    }
}
