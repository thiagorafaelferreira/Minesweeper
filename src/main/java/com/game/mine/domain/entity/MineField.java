package com.game.mine.domain.entity;

import java.util.List;

import com.game.mine.domain.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MineField {
    private Field[][] mineField;
    private Status status;
    @Getter
    private List<PositionField> positionsMine;
}
