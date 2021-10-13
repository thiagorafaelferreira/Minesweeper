package com.game.mine.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.mine.domain.builder.MineFieldBuilder;
import com.game.mine.domain.converter.MineFieldConverter;
import com.game.mine.domain.entity.Field;
import com.game.mine.domain.entity.GameMatch;
import com.game.mine.domain.entity.PositionField;
import com.game.mine.domain.entity.enums.Status;
import com.game.mine.infrastracture.repository.GameMatchRepository;
import com.game.mine.infrastracture.repository.MineFieldRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MineService {

    private MineFieldBuilder mineFieldBuilder;
    private MineFieldRepository mineFieldRepository;
    private GameMatchRepository gameMatchRepository;

    @Autowired
    public MineService(MineFieldBuilder mineFieldBuilder, MineFieldRepository mineFieldRepository, GameMatchRepository gameMatchRepository) {
        this.mineFieldBuilder = mineFieldBuilder;
        this.mineFieldRepository = mineFieldRepository;
        this.gameMatchRepository = gameMatchRepository;
    }

    public GameMatch continueGame(String userId) throws Exception {
        log.info("parameters to flag position userId: {} row: {} column: {} ", userId.toString());
        GameMatch gameMatchUser = instanciateGameMatchFromDatabase(userId);

        return gameMatchUser;
    }

    @Transactional
    public void reset(String userId) {
        gameMatchRepository.deleteByUserId(userId);
        mineFieldRepository.deleteByUserId(userId);
    }

    public void saveGameMatch(GameMatch gameMatch) {
        gameMatchRepository.save(gameMatch);
        List<Field> fields = MineFieldConverter.fieldMatrizToList(gameMatch.getMineField());
        fillUserId(gameMatch.getUserId(), fields);
        mineFieldRepository.saveAll(fields);
    }

    private void updatePositon(GameMatch gameMatch) {
        List<Field> fields = MineFieldConverter.fieldMatrizToList(gameMatch.getMineField());
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

    public GameMatch instanciateGameMatchFromDatabase(String userId) throws Exception {
        log.info("User id used to start game: {} ", userId.toString());

        GameMatch gameMatchUser = gameMatchRepository.findByUserId(userId);
        log.info("finded game match from userId: {} ", gameMatchUser != null);
        if(gameMatchUser == null) throw new Exception("GAME_OVER");

        List<Field> fields = mineFieldRepository.findByUserId(userId);
        if(fields == null  || fields.size() == 0) throw new Exception("GAME_OVER");

        Field[][] mineField = MineFieldConverter.fieldListToMatriz(fields);
        gameMatchUser.setMineField(mineField);
        return gameMatchUser;
    }

    public GameMatch flagPosition(String userId, Integer row, Integer column) throws Exception {
        log.info("parameters to flag position userId: {} row: {} column: {} ", userId.toString(), row.toString(), column.toString());
        GameMatch gameMatchUser = instanciateGameMatchFromDatabase(userId);
        if(gameMatchUser.getMineField()[row][column].isFlaged() || gameMatchUser.getMineField()[row][column].isClicked())
            return gameMatchUser;

        gameMatchUser.getMineField()[row][column].setFlaged(true);
        updatePositon(gameMatchUser);
        return gameMatchUser;
    }

    public GameMatch clickPosition(String userId, Integer row, Integer column) throws Exception {
        log.info("parameters to flag position userId: {} row: {} column: {} ", userId.toString(), row.toString(), column.toString());
        GameMatch gameMatchUser = instanciateGameMatchFromDatabase(userId);
        if(gameMatchUser.getMineField()[row][column].isFlaged() || gameMatchUser.getMineField()[row][column].isClicked())
            return gameMatchUser;

        if(gameMatchUser.getMineField()[row][column].isMine()) {
            gameMatchUser.setStatus(Status.GAME_OVER);
            return gameMatchUser;
        }

        analyzeNeighbour(gameMatchUser, row, column, null);
        updatePositon(gameMatchUser);
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
        int rangeEnd = 2;
        int rangeStart = -1;
        IntStream.range(rangeStart, rangeEnd).forEach(x -> {
            IntStream.range(rangeStart, rangeEnd).forEach(y ->
                    identifyAreMineArround(gameMatchAnalyzed, row, column, x, y, positions, mineArround)
            );
        });

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
        if(moveX == 0 && moveY == 0) return;

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
