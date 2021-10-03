package com.game.mine.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.mine.domain.builder.MineFieldBuilder;
import com.game.mine.domain.entity.MineField;

@Service
public class MineService {

    private MineFieldBuilder mineFieldBuilder;

    public MineService(MineFieldBuilder mineFieldBuilder) {
        this.mineFieldBuilder = mineFieldBuilder;
    }

    public MineField createMineField(Integer rows, Integer columns, Integer numberMines) {
        return mineFieldBuilder
                .rows(rows)
                .columns(columns)
                .mines(numberMines)
                .build();
    }
}
