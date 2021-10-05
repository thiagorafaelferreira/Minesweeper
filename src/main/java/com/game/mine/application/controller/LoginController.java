package com.game.mine.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.mine.application.entity.request.LoginRequest;
import com.game.mine.application.entity.response.LoginResponse;
import com.game.mine.domain.entity.User;
import com.game.mine.domain.service.LoginService;

@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
        User user = this.loginService.login(login.getUsername(), login.getPassword());
        return ResponseEntity.ok(new LoginResponse(user.getUsername(), "32o4jon4bto"));
    }
}
