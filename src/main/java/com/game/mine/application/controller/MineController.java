package com.game.mine.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.game.mine.application.entity.request.ParametersNewGameRequest;
import com.game.mine.application.entity.response.MineFieldResponse;
import com.game.mine.application.mapper.MineFieldMapper;
import com.game.mine.domain.entity.GameMatch;
import com.game.mine.domain.service.MineService;

@RestController
public class MineController {

    private MineService mineService;

    @Autowired
    public MineController(MineService mineService) {
        this.mineService = mineService;
    }

    @PostMapping("new_game")
    public ResponseEntity<MineFieldResponse> newGame(@RequestHeader("user_id") String userId, @RequestBody ParametersNewGameRequest parametersNewGameRequest) {
        GameMatch gameMatch =  mineService.createGameMatch(
                parametersNewGameRequest.getNumberRows(),
                parametersNewGameRequest.getNumberColumns(),
                parametersNewGameRequest.getNumberMinesOnField());
        gameMatch.setUserId(userId);
        mineService.saveGameMatch(gameMatch);
        MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
        return ResponseEntity.ok(mineFieldResponse);
    }

    @PutMapping("flag_position/{row}/{column}")
    public ResponseEntity<MineFieldResponse> flagPosition(@RequestHeader("user_id") String userId, @PathVariable Integer row, @PathVariable Integer column) {
        GameMatch gameMatch = mineService.flagPosition(userId, row, column);
        MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
        return ResponseEntity.ok(mineFieldResponse);
    }

    @PutMapping("click_position/{row}/{column}")
    public ResponseEntity<MineFieldResponse> clickPosition(@RequestHeader("user_id") String userId, @PathVariable Integer row, @PathVariable Integer column) {
        GameMatch gameMatch = mineService.clickPosition(userId, row, column);
        MineFieldResponse mineFieldResponse = MineFieldMapper.parse(gameMatch);
        return ResponseEntity.ok(mineFieldResponse);
    }

}
