package com.game.mine.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.mine.application.entity.request.LoginRequest;
import com.game.mine.application.entity.response.LoginResponse;

@RestController
public class LoginController {

    @GetMapping("login")
    public ResponseEntity<LoginResponse> login(LoginRequest login) {
        return ResponseEntity.ok(new LoginResponse("thiagoraf", "32o4jon4bto"));
    }
}
