package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.model.AuthResponse;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RegisterRequest registerRequest;
    private final LoginRequest loginRequest;

  @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        Optional<User> existingUser=userRepository.findByEmail(registerRequest.getEmail());
        if(existingUser.isPresent()){
            return AuthResponse.builder()
                    .message("User with this Email Already Exists")
                    .isSuccess(false)
                    .build();
        }
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User newUser = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(hashedPassword) // Crucial: use the hashed password
                .originVillage(registerRequest.getOriginVillage())
                .district(registerRequest.getDistrict())
                .state(registerRequest.getState())
                .ancestorNames(registerRequest.getAncestorNames())
                .build();
        userRepository.save(newUser);
        String token = jwtService.generateToken(newUser.getEmail());
        return AuthResponse.builder()
                .token(token)
                .message("Successfully Registered")
                .isSuccess(true)
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
     Optional<User>userOptional=userRepository.findByEmail(loginRequest.getEmail());
     if(userOptional.isEmpty()){
         return AuthResponse.builder()
                         .message("User is not Registered")
                                 .isSuccess(false)
                                         .build();
     }
        User foundUser = userOptional.get();
     boolean isPasswordMatch=passwordEncoder.matches(loginRequest.getPassword(), foundUser.getPassword());
        if (isPasswordMatch) {
            String token= jwtService.generateToken(foundUser.getEmail());
            return AuthResponse.builder().
                    token(token)
                    .message("login success")
                    .isSuccess(true)
                    .build();
        } else {
            return AuthResponse.builder()
                    .message("Incorrect Password")
                    .isSuccess(false)
                    .build();
        }
    }
}
