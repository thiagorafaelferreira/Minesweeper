package com.game.mine.application.entity.response;

import com.game.mine.domain.entity.Field;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MineFieldResponse {
    private Field mineField[][];
}
