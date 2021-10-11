package com.game.mine.application.mapper;

import com.game.mine.application.entity.response.MineFieldResponse;
import com.game.mine.domain.entity.GameMatch;

public class MineFieldMapper {
    public static MineFieldResponse parse(GameMatch gameMatch) {
        return MineFieldResponse.builder()
                .mineField(gameMatch.getMineField())
                .status(gameMatch.getStatus())
                .build();
    }
}
