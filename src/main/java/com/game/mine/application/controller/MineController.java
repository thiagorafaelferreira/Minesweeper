package com.game.mine.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.game.mine.application.entity.request.ParametersNewGameRequest;
import com.game.mine.application.entity.response.MineFieldResponse;
import com.game.mine.application.mapper.MineFieldMapper;
import com.game.mine.domain.entity.MineField;
import com.game.mine.domain.service.MineService;

@RestController
public class MineController {

    private MineService mineService;

    @Autowired
    public MineController(MineService mineService) {
        this.mineService = mineService;
    }

    @PostMapping("new_game")
    public ResponseEntity<MineFieldResponse> newGame(@RequestBody ParametersNewGameRequest parametersNewGameRequest) {
        MineField mineField =  mineService.createMineField(
                parametersNewGameRequest.getNumberRows(),
                parametersNewGameRequest.getNumberColumns(),
                parametersNewGameRequest.getNumberMinesOnField());
        MineFieldResponse mineFieldResponse = MineFieldMapper.parse(mineField);
        return ResponseEntity.ok(mineFieldResponse);
    }

    @PutMapping("flag_position/{row}/{column}")
    public ResponseEntity<String> flagPosition(@RequestParam Integer row, @RequestParam Integer column) {
        return ResponseEntity.ok("marcado flag");
    }

    @PutMapping("click_position/{row}/{column}")
    public ResponseEntity<String> clickPosition(@RequestParam Integer row, @RequestParam Integer column) {
        return ResponseEntity.ok("marcado flag");
    }

}
