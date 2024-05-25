package com.zzaip.monopoly.game_logic.api;

import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.game_logic.logic.GameLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameApiController {

    private final GameLogicService gameLogicService;

    @PostMapping("/host")
    public ResponseEntity<GameDTO> hostGame(@RequestParam String playerName) {
        GameDTO gameDTO = gameLogicService.hostGame(playerName);
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/join")
    public ResponseEntity<GameDTO> joinGame(@RequestParam String playerName, @RequestParam String hostURL) {
        GameDTO gameDTO = gameLogicService.joinGame(playerName, hostURL);
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/addPlayer")
    public ResponseEntity<Long> addPlayer(@RequestParam String playerName) {
        Long playerId = gameLogicService.addPlayer(playerName);
        return ResponseEntity.ok(playerId);
    }

    @PostMapping("/start")
    public ResponseEntity<GameDTO> startGame() {
        GameDTO gameDTO = gameLogicService.startGame();
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/end")
    public ResponseEntity<GameDTO> endGame() {
        GameDTO gameDTO = gameLogicService.endGame();
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/startTurn")
    public ResponseEntity<GameDTO> startTurn() {
        GameDTO gameDTO = gameLogicService.startTurn();
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/receiveUpdate")
    public ResponseEntity<Void> receiveGameUpdate(@RequestBody GameDTO gameDTO) {
        gameLogicService.receiveGameUpdate(gameDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/snapshot")
    public ResponseEntity<GameDTO> getActiveGameSnapshot() {
        GameDTO gameDTO = gameLogicService.getActiveGameSnapshot();
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/buyHouse")
    public ResponseEntity<GameDTO> buyHouse(@RequestParam int fieldNumber) {
        GameDTO gameDTO = gameLogicService.buyHouse(fieldNumber);
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/buyProperty")
    public ResponseEntity<GameDTO> buyProperty() {
        GameDTO gameDTO = gameLogicService.buyProperty();
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/endTurn")
    public ResponseEntity<GameDTO> endTurn() {
        GameDTO gameDTO = gameLogicService.endTurn();
        return ResponseEntity.ok(gameDTO);
    }
}
