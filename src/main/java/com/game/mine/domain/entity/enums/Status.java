package com.game.mine.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {
    PLAYING("PLAYING"),
    GAME_OVER("GAME_OVER");

    @Getter private String value;
}
