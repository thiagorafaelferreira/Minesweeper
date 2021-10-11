package com.game.mine.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.game.mine.application.entity.request.ParametersNewGameRequest;
import com.game.mine.application.entity.response.MineFieldResponse;
import com.game.mine.application.mapper.MineFieldMapper;
import com.game.mine.domain.entity.GameMatch;
import com.game.mine.domain.entity.enums.Status;
import com.game.mine.domain.service.MineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="Game actions")
@Slf4j
@CrossOrigin
@RestController
public class MineController {

    private MineService mineService;
    private String messageError = "error to bring game to continue, could been ended or it haven't been started";

    @Autowired
    public MineController(MineService mineService) {
        this.mineService = mineService;
    }


    @ApiOperation(value="bring the game that have been played, last state to be continued", response = MineFieldResponse.class)
    @GetMapping("continue_game")
    public ResponseEntity<MineFieldResponse> continueGame(@RequestHeader("user_id") String userId) {
        try {
            GameMatch gameMatch = mineService.continueGame(userId);
            MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
            return ResponseEntity.ok(mineFieldResponse);
        } catch (Exception e) {
            log.error("error to bring game to continue, could been ended or it haven't been started", e);
            return ResponseEntity.ok(MineFieldResponse.builder().status(Status.GAME_OVER).build());
        }
    }

    @ApiOperation(value="remove the saved game, the state the game that have been played", response = String.class)
    @DeleteMapping("reset_game")
    public ResponseEntity<String> resetGame(@RequestHeader("user_id") String userId) {
        mineService.reset(userId);
        return ResponseEntity.ok("DELETED");
    }

    @ApiOperation(value="start a new game if it was not started yet", response = MineFieldResponse.class)
    @PostMapping("new_game")
    public ResponseEntity<MineFieldResponse> newGame(@RequestHeader("user_id") String userId, @RequestBody ParametersNewGameRequest parametersNewGameRequest) {
        try {
            GameMatch gameMatch = mineService.continueGame(userId);
            if(gameMatch != null && gameMatch.getStatus().equals(Status.PLAYING)) {
                MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
                return ResponseEntity.ok(mineFieldResponse);
            }
            return newGameFacade(userId, parametersNewGameRequest);
        } catch (Exception e) {
            log.error("error to bring game to continue, could been ended or it haven't been started", e);
            return newGameFacade(userId, parametersNewGameRequest);
        }
    }

    public  ResponseEntity<MineFieldResponse> newGameFacade(String userId, ParametersNewGameRequest parametersNewGameRequest) {
        GameMatch gameMatch = mineService.createGameMatch(parametersNewGameRequest.getNumberRows(),
                parametersNewGameRequest.getNumberColumns(), parametersNewGameRequest.getNumberMinesOnField());
        gameMatch.setUserId(userId);
        mineService.saveGameMatch(gameMatch);
        MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
        return ResponseEntity.ok(mineFieldResponse);
    }

    @ApiOperation(value="put flag on position clicked", response = MineFieldResponse.class)
    @PutMapping("flag_position/{row}/{column}")
    public ResponseEntity<MineFieldResponse> flagPosition(@RequestHeader("user_id") String userId, @PathVariable Integer row, @PathVariable Integer column) {
        try {
            GameMatch gameMatch = mineService.flagPosition(userId, row, column);
            MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
            return ResponseEntity.ok(mineFieldResponse);
        } catch (Exception e) {
            log.error("error to bring game to continue, could been ended or it haven't been started", e);
            return ResponseEntity.ok(MineFieldResponse.builder().status(Status.GAME_OVER).build());
        }
    }

    @ApiOperation(value="click on one position and analyze neighbours", response = MineFieldResponse.class)
    @PutMapping("click_position/{row}/{column}")
    public ResponseEntity<MineFieldResponse> clickPosition(@RequestHeader("user_id") String userId, @PathVariable Integer row, @PathVariable Integer column) {
         try {
            GameMatch gameMatch = mineService.clickPosition(userId, row, column);
            MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
            return ResponseEntity.ok(mineFieldResponse);
        } catch (Exception e) {
            log.error("error to bring game to continue, could been ended or it haven't been started", e);
            return ResponseEntity.ok(MineFieldResponse.builder().status(Status.GAME_OVER).build());
        }
    }
}
