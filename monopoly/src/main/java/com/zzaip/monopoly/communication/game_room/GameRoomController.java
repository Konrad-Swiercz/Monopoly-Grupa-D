package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game-rooms")
@RequiredArgsConstructor
public class GameRoomController {

    private final GameRoomServiceImpl gameRoomService;

    @GetMapping
    public ResponseEntity<List<GameRoom>> getAllGameRooms() {
        return ResponseEntity.ok(gameRoomService.getAllGameRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameRoom> getGameRoomById(@PathVariable long id) {
        GameRoom gameRoom = gameRoomService.getGameRoomById(id);
        return gameRoom != null ? ResponseEntity.ok(gameRoom) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GameRoom> createGameRoom(@RequestBody GameRoom gameRoom) {
        return ResponseEntity.ok(gameRoomService.createGameRoom(gameRoom));
    }


    @PutMapping("/{id}")
    public ResponseEntity<GameRoom> updateGameRoom(@PathVariable long id, @RequestBody GameRoom gameRoom) {
        if (gameRoom.getGameRoomId() != id) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(gameRoomService.updateGameRoom(gameRoom));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<GameRoom> getActiveGameRoom() {
        GameRoom activeGameRoom = gameRoomService.getActiveGameRoom();
        return activeGameRoom != null ? ResponseEntity.ok(activeGameRoom) : ResponseEntity.notFound().build();
    }

    @GetMapping("/active-players-urls")
    public ResponseEntity<List<String>> getActivePlayersURLs() {
        return ResponseEntity.ok(gameRoomService.getActivePlayersURLs());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameRoom(@PathVariable long id) {
        gameRoomService.deleteGameRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<GameRoom> joinGameRoom(@PathVariable long id, @RequestBody PlayerConnection playerConnection) {
        GameRoom gameRoom = gameRoomService.getGameRoomById(id);
        if (gameRoom != null) {
            try {
                return ResponseEntity.ok(gameRoomService.joinGameRoom(gameRoom, playerConnection));
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<GameRoom> leaveGameRoom(@PathVariable long id, @RequestBody PlayerConnection playerConnection) {
        GameRoom gameRoom = gameRoomService.getGameRoomById(id);
        if (gameRoom != null) {
            return ResponseEntity.ok(gameRoomService.leaveGameRoom(gameRoom, playerConnection));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
