package com.game.mine.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.mine.domain.builder.MineFieldBuilder;
import com.game.mine.domain.converter.MineFieldConverter;
import com.game.mine.domain.entity.Field;
import com.game.mine.domain.entity.GameMatch;
import com.game.mine.domain.entity.PositionField;
import com.game.mine.domain.entity.enums.Status;
import com.game.mine.infrastracture.repository.GameMatchRepository;
import com.game.mine.infrastracture.repository.MineFieldRepository;
import lombok.Setter;

@Service
public class MineService {

    private MineFieldBuilder mineFieldBuilder;
    private MineFieldRepository mineFieldRepository;
    private GameMatchRepository gameMatchRepository;
    @Setter
    private GameMatch gameMatch;

    @Autowired
    public MineService(MineFieldBuilder mineFieldBuilder, MineFieldRepository mineFieldRepository, GameMatchRepository gameMatchRepository) {
        this.mineFieldBuilder = mineFieldBuilder;
        this.mineFieldRepository = mineFieldRepository;
        this.gameMatchRepository = gameMatchRepository;
    }

    public void saveGameMatch(GameMatch gameMatch) {
        gameMatchRepository.save(gameMatch);
        List<Field> fields = MineFieldConverter.fieldMatrizToList(gameMatch.getMineField());
        fillUserId(gameMatch.getUserId(), fields);
        mineFieldRepository.saveAll(fields);
    }

    private void fillUserId(String userId, List<Field> fields) {
        fields.forEach(field -> field.setUserId(userId));
    }

    public GameMatch createGameMatch(Integer rows, Integer columns, Integer numberMines) {
        return mineFieldBuilder
                .rows(rows)
                .columns(columns)
                .mines(numberMines)
                .build();
    }

    public GameMatch instanciateGameMatchFromDatabase(String userId) {
        GameMatch gameMatchUser = gameMatchRepository.findByUserId(userId);
        List<Field> fields = mineFieldRepository.findByUserId(userId);
        Field[][] mineField = MineFieldConverter.fieldListToMatriz(fields);
        gameMatchUser.setMineField(mineField);
        return gameMatchUser;
    }

    public GameMatch flagPosition(String userId, Integer row, Integer column) {
        GameMatch gameMatchUser = instanciateGameMatchFromDatabase(userId);
        if(gameMatchUser.getMineField()[row][column].isFlaged() || gameMatchUser.getMineField()[row][column].isClicked())
            return gameMatchUser;

        gameMatchUser.getMineField()[row][column].setFlaged(true);
        return gameMatchUser;
    }

    public GameMatch clickPosition(String userId, Integer row, Integer column) {
        GameMatch gameMatchUser = instanciateGameMatchFromDatabase(userId);
        if(gameMatchUser.getMineField()[row][column].isMine()) {
            gameMatchUser.setStatus(Status.GAME_OVER);
            return gameMatchUser;
        }

        if(gameMatchUser.getMineField()[row][column].isFlaged() || gameMatchUser.getMineField()[row][column].isClicked())
            return gameMatchUser;

        analyzeNeighbour(gameMatchUser, row, column, null);
        return gameMatchUser;
    }

    public GameMatch analyzeNeighbour(GameMatch gameMatchAnalyzed, Integer row, Integer column, List<PositionField> evaluatedPositions) {
        if(evaluatedPositions == null) {
            evaluatedPositions = new ArrayList<>();
        } else {
            evaluatedPositions.add(new PositionField(row, column));
        }

        AtomicInteger mineArround = new AtomicInteger();
        List<PositionField> positions = new ArrayList<>();
        identifyAreMineArround(gameMatchAnalyzed, row, column, 1, 1, positions, mineArround);
        identifyAreMineArround(gameMatchAnalyzed, row, column, 1, 0, positions, mineArround);
        identifyAreMineArround(gameMatchAnalyzed, row, column, 1, -1, positions, mineArround);
        identifyAreMineArround(gameMatchAnalyzed, row, column, 0, 1, positions, mineArround);
        identifyAreMineArround(gameMatchAnalyzed, row, column, 0, -1, positions, mineArround);
        identifyAreMineArround(gameMatchAnalyzed, row, column, -1, 1, positions, mineArround);
        identifyAreMineArround(gameMatchAnalyzed, row, column, -1, 0, positions, mineArround);
        identifyAreMineArround(gameMatchAnalyzed, row, column, -1, -1, positions, mineArround);


        gameMatchAnalyzed.getMineField()[row][column].setClicked(true);
        gameMatchAnalyzed.getMineField()[row][column].setMinesArround(mineArround.get());

        if(mineArround.get() == 0) {
            List<PositionField> finalEvaluatedPositions = evaluatedPositions;
            positions.forEach(position -> {
                List<PositionField> positionsFiltered = finalEvaluatedPositions.stream()
                        .filter(positionField -> position.getRow()
                                .equals(positionField.getRow()) && position.getColumn()
                                .equals(positionField.getColumn())).collect(Collectors.toList());
                if(positionsFiltered.size() == 0) {
                    System.out.println("position " + position.getRow() + " " + position.getColumn());
                    analyzeNeighbour(gameMatchAnalyzed, position.getRow(), position.getColumn(), finalEvaluatedPositions);
                }
            });
        }

        return gameMatchAnalyzed;
    }

    public void identifyAreMineArround(GameMatch gameMatch, Integer row, Integer column, Integer moveX, Integer moveY, List<PositionField> positions, AtomicInteger mineArround) {
        Integer rowNeighbour = 0;
        Integer columnNeighbour = 0;

        rowNeighbour = row - moveX;
        columnNeighbour = column - moveY;

        if(rowNeighbour >= 0 && columnNeighbour >= 0 && rowNeighbour < gameMatch.getMineField().length && columnNeighbour < gameMatch
                .getMineField()[0].length) {
            if(gameMatch.getMineField()[rowNeighbour][columnNeighbour].isMine()) {
                mineArround.getAndIncrement();
            } else {
                positions.add(new PositionField(rowNeighbour, columnNeighbour));
            }
        }
    }
}
