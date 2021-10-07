package com.game.mine.infrastracture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.mine.domain.entity.Field;

public interface MineFieldRepository extends JpaRepository<Field, Integer> {
    List<Field> findByUserId(String userId);
}
