package com.game.mine.domain.service;



import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.game.mine.domain.builder.MineFieldBuilder;
import com.game.mine.domain.converter.MineFieldConverter;
import com.game.mine.domain.entity.Field;
import com.game.mine.domain.entity.GameMatch;
import com.game.mine.domain.entity.enums.Status;
import com.game.mine.infrastracture.repository.GameMatchRepository;
import com.game.mine.infrastracture.repository.MineFieldRepository;

public class MineServiceTest {

    private MineFieldRepository mineFieldRepositoryMock;
    private GameMatchRepository gameMatchRepository;
    private MineService mineService;
    private GameMatch gameMatch;

    @BeforeEach
    public void init() {
        MineFieldRepository mineFieldRepositoryMock = Mockito.mock(MineFieldRepository.class);
        GameMatchRepository gameMatchRepositoryMock = Mockito.mock(GameMatchRepository.class);
        mineService = new MineService(new MineFieldBuilder(), mineFieldRepositoryMock, gameMatchRepositoryMock);
        gameMatch = mineService.createGameMatch(10, 10, 3);
        when(gameMatchRepositoryMock.findByUserId(anyString())).thenReturn(gameMatch);
        List<Field> fields = MineFieldConverter.fieldMatrizToList(gameMatch.getMineField());
        when(mineFieldRepositoryMock.findByUserId(anyString())).thenReturn(fields);
    }

    @Test
    public void creatingFieldTest() {
        displayMinField(gameMatch);
    }

    @Test
    public void creatingFieldPositionsTest() {
        displayMinFieldPositions(gameMatch);
    }

    @Test
    public void creatingFieldShowingMinesTest() {
        displayMinesOnMineField(gameMatch);
    }

    private void displayMinFieldPositions(GameMatch gameMatch) {
        for(int row = 0; row < gameMatch.getMineField().length; row++) {
            StringBuffer rowBuffer = new StringBuffer();
            for(int column = 0; column < gameMatch.getMineField()[row].length; column++) {
                rowBuffer.append(row + "" + column + " ");
            }
            System.out.println(rowBuffer);
        }
        System.out.println();
    }

    private void displayMinField(GameMatch gameMatch) {
        for(int row = 0; row < gameMatch.getMineField().length; row++) {
            StringBuffer rowBuffer = new StringBuffer();
            for(int column = 0; column < gameMatch.getMineField()[row].length; column++) {
                rowBuffer.append("0 ");
            }
            System.out.println(rowBuffer);
        }
        System.out.println();
    }

    private void displayMinesOnMineField(GameMatch gameMatch) {
        for(int row = 0; row < gameMatch.getMineField().length; row++) {
            StringBuffer rowBuffer = new StringBuffer();
            for(int column = 0; column < gameMatch.getMineField()[row].length; column++) {
                rowBuffer.append(
                        (gameMatch.getMineField()[row][column].isMine()
                                ? "9 "
                                : (
                                        gameMatch.getMineField()[row][column].isClicked() &&
                                        gameMatch.getMineField()[row][column].getMinesArround() == 0
                                                ? "8"
                                                : gameMatch.getMineField()[row][column].getMinesArround()
                                    ) + " "));
            }
            System.out.println(rowBuffer);
        }
        System.out.println();
    }

    @Test
    public void clickPositionThereIsMineTest() {
        displayMinesOnMineField(gameMatch);
        System.out.println(gameMatch.getPositionsMine().get(0).getRow());
        System.out.println(gameMatch.getPositionsMine().get(0).getColumn());

        GameMatch gameMatchResult = mineService.clickPosition("1fasdf2", gameMatch.getPositionsMine().get(0).getRow(),
                gameMatch.getPositionsMine().get(0).getColumn());
        displayMinesOnMineField(gameMatchResult);
        Assertions.assertEquals(Status.GAME_OVER.getValue(), gameMatch.getStatus().getValue());
    }

    @Test
    public void clickPositionTest() {
        displayMinesOnMineField(gameMatch);
        GameMatch gameMatchResultClick1 = mineService.clickPosition("1fasdf2", 0, 0);
        displayMinesOnMineField(gameMatchResultClick1);
    }
}
