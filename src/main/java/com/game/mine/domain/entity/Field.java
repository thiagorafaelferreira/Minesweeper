package com.game.mine.domain.entity;

import lombok.Data;

@Data
public class Field {
    private boolean flaged = false;
    private boolean mine = false;
    private boolean clicked = false;
    private Integer distanceMines;
}
