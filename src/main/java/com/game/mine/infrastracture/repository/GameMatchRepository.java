package com.game.mine.infrastracture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.mine.domain.entity.GameMatch;

public interface GameMatchRepository extends JpaRepository<GameMatch, Integer> {
    GameMatch findByUserId(String userId);
    void deleteByUserId(String userId);
}
