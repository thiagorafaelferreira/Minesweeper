package com.game.mine.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.mine.domain.entity.User;
import com.game.mine.infrastracture.repository.UserRepository;

@Service
public class LoginService {

    private UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        return this.userRepository.findByUsernameAndPassword(username, password);
    }
}
