package com.game.mine.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.mine.domain.builder.MineFieldBuilder;
import com.game.mine.domain.entity.Field;
import com.game.mine.domain.entity.MineField;
import com.game.mine.domain.entity.PositionField;
import com.game.mine.domain.entity.enums.Status;
import lombok.Setter;

@Service
public class MineService {

    private MineFieldBuilder mineFieldBuilder;
    @Setter
    private MineField mineField;

    @Autowired
    public MineService(MineFieldBuilder mineFieldBuilder) {
        this.mineFieldBuilder = mineFieldBuilder;
    }

    public MineField createMineField(Integer rows, Integer columns, Integer numberMines) {
        return mineFieldBuilder
                .rows(rows)
                .columns(columns)
                .mines(numberMines)
                .build();
    }

    public MineField flagPosition(Integer row, Integer column) {
        // TODO remover depois e fazer chamada a um banco relacional esta usando variavel global
        if(mineField.getMineField()[row][column].isFlaged() || mineField.getMineField()[row][column].isClicked())
            return mineField;

        mineField.getMineField()[row][column].setFlaged(true);
        return mineField;
    }

    public MineField clickPosition(Integer row, Integer column) {
        // TODO remover depois e fazer chamada a um banco relacional esta usando variavel global
        if(mineField.getMineField()[row][column].isMine()) {
            mineField.setStatus(Status.GAME_OVER);
            return mineField;
        }

        if(mineField.getMineField()[row][column].isFlaged() || mineField.getMineField()[row][column].isClicked())
            return mineField;

        analyzeNeighbour(mineField, row, column, null);
        return mineField;
    }

    public MineField analyzeNeighbour(MineField mineField, Integer row, Integer column, List<PositionField> evaluatedPositions) {
        if(evaluatedPositions == null) {
            evaluatedPositions = new ArrayList<>();
        } else {
            evaluatedPositions.add(new PositionField(row, column));
        }

        System.out.println("inicial position " + row + " " + column);

        AtomicInteger mineArround = new AtomicInteger();
        List<PositionField> positions = new ArrayList<>();
        identifyAreMineArround(mineField, row, column, 1, 1, positions, mineArround);
        identifyAreMineArround(mineField, row, column, 1, 0, positions, mineArround);
        identifyAreMineArround(mineField, row, column, 1, -1, positions, mineArround);
        identifyAreMineArround(mineField, row, column, 0, 1, positions, mineArround);
        identifyAreMineArround(mineField, row, column, 0, -1, positions, mineArround);
        identifyAreMineArround(mineField, row, column, -1, 1, positions, mineArround);
        identifyAreMineArround(mineField, row, column, -1, 0, positions, mineArround);
        identifyAreMineArround(mineField, row, column, -1, -1, positions, mineArround);


        mineField.getMineField()[row][column].setClicked(true);
        mineField.getMineField()[row][column].setDistanceMines(mineArround.get());
        System.out.println("mine arround " + mineArround.get());
        if(mineArround.get() == 0) {
            List<PositionField> finalEvaluatedPositions = evaluatedPositions;
            positions.forEach(position -> {
                List<PositionField> positionsFiltered = finalEvaluatedPositions.stream()
                        .filter(positionField -> position.getRow()
                                .equals(positionField.getRow()) && position.getColumn()
                                .equals(positionField.getColumn())).collect(Collectors.toList());
                if(positionsFiltered.size() == 0) {
                    System.out.println("position " + position.getRow() + " " + position.getColumn());
                    analyzeNeighbour(mineField, position.getRow(), position.getColumn(), finalEvaluatedPositions);
                }
            });
        }

        return mineField;
    }

    public void identifyAreMineArround(MineField mineField, Integer row, Integer column, Integer moveX, Integer moveY, List<PositionField> positions, AtomicInteger mineArround) {
        Integer rowNeighbour = 0;
        Integer columnNeighbour = 0;

        rowNeighbour = row - moveX;
        columnNeighbour = column - moveY;
        System.out.println("neighbour " + rowNeighbour + " " + columnNeighbour);
        if(rowNeighbour >= 0 && columnNeighbour >= 0 && rowNeighbour < mineField.getMineField().length && columnNeighbour < mineField.getMineField()[0].length) {
            if(mineField.getMineField()[rowNeighbour][columnNeighbour].isMine()) {
                mineArround.getAndIncrement();
            } else {
                positions.add(new PositionField(rowNeighbour, columnNeighbour));
            }
        }
    }
}
