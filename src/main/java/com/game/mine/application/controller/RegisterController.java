package com.game.mine.application.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.mine.application.entity.response.LoginResponse;
import com.game.mine.application.entity.request.RegisterRequest;

@RestController
public class RegisterController {

    @PostMapping("register")
    public ResponseEntity<LoginResponse> register(RegisterRequest registerRequest) {
        return ResponseEntity.ok(new LoginResponse("thiagoraf", "32o4jon4bto", UUID.randomUUID()));
    }

    @PutMapping("register")
    public ResponseEntity<LoginResponse> completeRegister(RegisterRequest registerRequest) {
        return ResponseEntity.ok(new LoginResponse("thiagoraf", "32o4jon4bto", UUID.randomUUID()));
    }
}
