package com.game.mine.application.mapper;

import com.game.mine.application.entity.response.MineFieldResponse;
import com.game.mine.domain.entity.MineField;

public class MineFieldMapper {

    public static MineFieldResponse parse(MineField mineField) {
        return new MineFieldResponse(mineField.getMineField(), mineField.getStatus());
    }
}
