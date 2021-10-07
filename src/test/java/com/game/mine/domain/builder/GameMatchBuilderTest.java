package com.game.mine.domain.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.game.mine.domain.entity.GameMatch;

public class GameMatchBuilderTest {

    @Test
    public void mineFieldBuiltWithoutMineTest() {
        GameMatch gameMatch = new MineFieldBuilder().rows(10).columns(10).build();
        Assertions.assertEquals(10, gameMatch.getMineField().length);
        Assertions.assertEquals(10, gameMatch.getMineField()[0].length);
        Assertions.assertEquals(false, gameMatch.getMineField()[0][0].isMine());
    }

    @Test
    public void mineFieldBuildTest() {
        GameMatch gameMatch = new MineFieldBuilder().rows(10).columns(10).mines(3).build();
        Assertions.assertEquals(10, gameMatch.getMineField().length);
        Assertions.assertEquals(10, gameMatch.getMineField()[0].length);
    }

    @Test
    public void wereSetCorrectAmountMinesTest() {
        GameMatch gameMatch = new MineFieldBuilder().rows(10).columns(10).mines(3).build();
        Integer mineCount = 0;
        for(int row = 0; row < gameMatch.getMineField().length; row++) {
            for(int column = 0; column < gameMatch.getMineField()[0].length; column++) {
                if(gameMatch.getMineField()[row][column].isMine()) {
                    mineCount++;
                }
            }
        }
        Assertions.assertEquals(3, mineCount);
    }
}
