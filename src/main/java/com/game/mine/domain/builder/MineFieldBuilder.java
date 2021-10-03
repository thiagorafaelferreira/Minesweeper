package com.game.mine.domain.builder;

import java.util.Random;

import com.game.mine.domain.entity.Field;
import com.game.mine.domain.entity.MineField;

public class MineFieldBuilder {
    private Integer columns;
    private Integer rows;
    private Integer numberMines;

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

    public MineField build() {
        Field[][] mineField = new Field[rows][columns];
        initiateMineFied(mineField);
        putMineOnField(mineField);
        return new MineField(mineField);
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
            } else {
                mine--;
            }
        }
    }

    private void initiateMineFied(Field[][] mineField) {
        for(int row = 0; row < rows; row++) {
            for(int column = 0; column < columns; column++) {
                mineField[row][column] = new Field();
            }
        }
    }
}
