package com.example.demo.controller;

import com.example.demo.model.AuthResponse;
import com.example.demo.model.RegisterRequest;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AuthController {
   @Autowired
   private AuthService authService;
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
     AuthResponse response=authService.register(request);
     if(response.isSuccess()){
         return  ResponseEntity.ok(response);
     }
     else {
         return ResponseEntity.badRequest().body(response);
     }
    }


}
