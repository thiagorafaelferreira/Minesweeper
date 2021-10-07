package com.game.mine.domain.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.game.mine.domain.entity.Field;
import com.game.mine.domain.entity.GameMatch;
import com.game.mine.domain.entity.PositionField;
import com.game.mine.domain.entity.enums.Status;

@Component
public class MineFieldBuilder {
    private Integer columns;
    private Integer rows;
    private Integer numberMines;
    private List<PositionField> positionsMine;

    public MineFieldBuilder() { this.positionsMine = new ArrayList<>(); }

    public MineFieldBuilder columns(Integer number) {
        this.columns = number;
        return this;
    }

    public MineFieldBuilder rows(Integer number) {
        this.rows = number;
        return this;
    }

    public MineFieldBuilder mines(Integer number) {
        this.numberMines = number;
        return this;
    }

    public GameMatch build() {
        Field[][] mineField = new Field[rows][columns];
        initiateMineFied(mineField);
        putMineOnField(mineField);
        return new GameMatch(mineField, Status.PLAYING, positionsMine);
    }

    private void putMineOnField(Field[][] mineField) {
        if(numberMines == null || numberMines == 0) return;

        for(int mine = 0; mine < numberMines; mine++) {
            Random random = new Random();
            Integer rowSelected = random.ints(0, rows)
                    .findFirst()
                    .getAsInt();

            Integer columnSelected = random.ints(0, columns)
                    .findFirst()
                    .getAsInt();

            if(mineField[rowSelected][columnSelected].isMine() != true) {
                mineField[rowSelected][columnSelected].setMine(true);
                positionsMine.add(new PositionField(rowSelected, columnSelected));
            } else {
                mine--;
            }
        }
    }

    private void initiateMineFied(Field[][] mineField) {
        for(int row = 0; row < rows; row++) {
            for(int column = 0; column < columns; column++) {
                Field field = new Field();
                field.setRowPosition(row);
                field.setColumnPosition(column);
                field.setMinesArround(0);
                mineField[row][column] = field;
            }
        }
    }
}
