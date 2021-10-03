package com.game.mine.domain.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.game.mine.domain.entity.MineField;

public class MineFieldBuilderTest {

    @Test
    public void mineFieldBuiltWithoutMineTest() {
        MineField mineField = new MineFieldBuilder().rows(10).columns(10).build();
        Assertions.assertEquals(10, mineField.getFiled().length);
        Assertions.assertEquals(10, mineField.getFiled()[0].length);
        Assertions.assertEquals(false, mineField.getFiled()[0][0].isMine());
    }

    @Test
    public void mineFieldBuildTest() {
        MineField mineField = new MineFieldBuilder().rows(10).columns(10).mines(3).build();
        Assertions.assertEquals(10, mineField.getFiled().length);
        Assertions.assertEquals(10, mineField.getFiled()[0].length);
    }

    @Test
    public void wereSetCorrectAmountMinesTest() {
        MineField mineField = new MineFieldBuilder().rows(10).columns(10).mines(3).build();
        Integer mineCount = 0;
        for(int row = 0; row < mineField.getFiled().length; row++) {
            for(int column = 0; column < mineField.getFiled()[0].length; column++) {
                if(mineField.getFiled()[row][column].isMine()) {
                    mineCount++;
                }
            }
        }
        Assertions.assertEquals(3, mineCount);
    }
}
