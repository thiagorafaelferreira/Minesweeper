package com.game.mine.infrastracture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.mine.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
}
