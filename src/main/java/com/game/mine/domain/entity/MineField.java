package com.game.mine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MineField {
    private Field[][] filed;
}
