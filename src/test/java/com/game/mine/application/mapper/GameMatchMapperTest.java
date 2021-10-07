package com.game.mine.application.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.game.mine.application.entity.response.MineFieldResponse;
import com.game.mine.domain.builder.MineFieldBuilder;
import com.game.mine.domain.entity.GameMatch;

public class GameMatchMapperTest {

    @Test
    public void mineFieldToMineFieldResponseTest() {
        GameMatch gameMatch = new MineFieldBuilder().rows(10).columns(10).build();
        MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);

        Assertions.assertEquals(gameMatch.getMineField().length, mineFieldResponse.getMineField().length);
        Assertions.assertEquals(gameMatch.getMineField()[0].length, mineFieldResponse.getMineField()[0].length);
        Assertions.assertEquals(gameMatch.getMineField()[0][0].isMine(), mineFieldResponse.getMineField()[0][0].isMine());
    }
}
