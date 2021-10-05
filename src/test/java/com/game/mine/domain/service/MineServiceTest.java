package com.game.mine.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.game.mine.domain.builder.MineFieldBuilder;
import com.game.mine.domain.entity.MineField;
import com.game.mine.domain.entity.enums.Status;

public class MineServiceTest {

    @Test
    public void creatingFieldTest() {
        MineService mineService = new MineService(new MineFieldBuilder());
        MineField mineField = mineService.createMineField(10, 10, 3);
        displayMinField(mineField);
    }

    @Test
    public void creatingFieldPositionsTest() {
        MineService mineService = new MineService(new MineFieldBuilder());
        MineField mineField = mineService.createMineField(10, 10, 3);
        displayMinFieldPositions(mineField);
    }

    @Test
    public void creatingFieldShowingMinesTest() {
        MineService mineService = new MineService(new MineFieldBuilder());
        MineField mineField = mineService.createMineField(10, 10, 3);
        displayMinesOnMineField(mineField);
    }

    private void displayMinFieldPositions(MineField mineField) {
        for(int row = 0; row < mineField.getMineField().length; row++) {
            StringBuffer rowBuffer = new StringBuffer();
            for(int column = 0; column < mineField.getMineField()[row].length; column++) {
                rowBuffer.append(row + "" + column + " ");
            }
            System.out.println(rowBuffer);
        }
        System.out.println();
    }

    private void displayMinField(MineField mineField) {
        for(int row = 0; row < mineField.getMineField().length; row++) {
            StringBuffer rowBuffer = new StringBuffer();
            for(int column = 0; column < mineField.getMineField()[row].length; column++) {
                rowBuffer.append("0 ");
            }
            System.out.println(rowBuffer);
        }
        System.out.println();
    }

    private void displayMinesOnMineField(MineField mineField) {
        for(int row = 0; row < mineField.getMineField().length; row++) {
            StringBuffer rowBuffer = new StringBuffer();
            for(int column = 0; column < mineField.getMineField()[row].length; column++) {
                rowBuffer.append(
                        (mineField.getMineField()[row][column].isMine()
                                ? "9 "
                                : (
                                        mineField.getMineField()[row][column].isClicked() &&
                                        mineField.getMineField()[row][column].getDistanceMines() == 0
                                                ? "8"
                                                : mineField.getMineField()[row][column].getDistanceMines()
                                    ) + " "));
            }
            System.out.println(rowBuffer);
        }
        System.out.println();
    }

    @Test
    public void clickPositionThereIsMineTest() {
        MineService mineService = new MineService(new MineFieldBuilder());
        MineField mineField = mineService.createMineField(10, 10, 3);
        mineService.setMineField(mineField);
        MineField mineFieldResult = mineService.clickPosition(mineField.getPositionsMine().get(0).getRow(),
                mineField.getPositionsMine().get(0).getColumn());
        Assertions.assertEquals(Status.GAME_OVER.getValue(), mineField.getStatus().getValue());
    }

    @Test
    public void clickPositionTest() {
        MineService mineService = new MineService(new MineFieldBuilder());
        MineField mineField = mineService.createMineField(10, 10, 20);
        mineService.setMineField(mineField);
        displayMinesOnMineField(mineField);
        MineField mineFieldResultClick1 = mineService.clickPosition(0, 0);
        displayMinesOnMineField(mineFieldResultClick1);
        MineField mineFieldResultClick2 = mineService.clickPosition(1, 7);
        displayMinesOnMineField(mineFieldResultClick2);
    }
}
