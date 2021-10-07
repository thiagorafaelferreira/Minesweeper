package com.game.mine.domain.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.game.mine.domain.entity.Field;

public class MineFieldConverter {

    public static List<Field> fieldMatrizToList(Field[][] mineField) {
        List<Field> fields = new ArrayList<>();
        for(int row = 0; row < mineField.length; row++) {
            for(int column = 0; column < mineField[row].length; column++) {
                fields.add(mineField[row][column]);
            }
        }
        return fields;
    }

    public static Field[][] fieldListToMatriz(List<Field> fields) {
        Integer rowSize = fields.stream().mapToInt(field -> field.getRowPosition()).max().orElseThrow(
                NoSuchElementException::new);

        Integer columnSize = fields.stream().mapToInt(field -> field.getColumnPosition()).max().orElseThrow(
                NoSuchElementException::new);

        Field[][] mineField = new Field[rowSize + 1][columnSize + 1];

        fields.forEach(field -> {
            mineField[field.getRowPosition()][field.getColumnPosition()] = field;
        });

        return mineField;
    }
}
