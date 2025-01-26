package com.beehome.taskmanagerapi.service;

import com.beehome.taskmanagerapi.dto.AuthRequest;
import com.beehome.taskmanagerapi.model.UserModel;
import com.beehome.taskmanagerapi.repository.AuthRepository;
import com.beehome.taskmanagerapi.security.JwtService;
import com.beehome.taskmanagerapi.util.CryptoUtil;
import com.beehome.taskmanagerapi.validate.AuthValidate;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AuthValidate authValidate;

    @Autowired
    private CryptoUtil cryptoUtil;

    public String login(AuthRequest credentials) {
        authValidate.login(credentials);

        Optional<UserModel> userOptional = authRepository.findUserByEmail(credentials.getEmail());

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            boolean passwordMatches = cryptoUtil.matches(credentials.getPassword(), user.getPassword());

            if (passwordMatches) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", user.getId());
                claims.put("email", user.getEmail());

                return jwtService.generateTokenWithClaims(claims);
            }
        }

        throw new ValidationException("Credenciais inválidas");
    }

}
