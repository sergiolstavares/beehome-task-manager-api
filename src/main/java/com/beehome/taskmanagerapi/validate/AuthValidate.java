package com.beehome.taskmanagerapi.validate;

import com.beehome.taskmanagerapi.dto.AuthRequest;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class AuthValidate {
    public void login(AuthRequest credentials) {
        if (credentials.getEmail() == null || credentials.getEmail().isEmpty()) {
            throw new ValidationException("O email deve ser informado");
        }

        if (credentials.getPassword() == null || credentials.getPassword().isEmpty()) {
            throw new ValidationException("A senha deve ser informada");
        }
    }
}
