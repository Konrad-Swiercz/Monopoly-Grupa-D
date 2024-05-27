package com.zzaip.monopoly.communication.connection;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player-connections")
@RequiredArgsConstructor
public class PlayerConnectionController {

    private final PlayerConnectionServiceImpl playerConnectionService;

    @GetMapping
    public ResponseEntity<List<PlayerConnection>> getPlayerConnections() {
        return ResponseEntity.ok(playerConnectionService.getPlayerConnections());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerConnection> getPlayerConnectionByPlayerId(@PathVariable long playerId) {
        try {
            PlayerConnection playerConnection = playerConnectionService.getPlayerConnectionByPlayerId(playerId);
            return ResponseEntity.ok(playerConnection);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PlayerConnection> createPlayerConnection(@RequestBody PlayerConnection playerConnection) {
        return ResponseEntity.ok(playerConnectionService.createPlayerConnection(playerConnection));
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerConnection> updatePlayerConnection(@PathVariable long playerId, @RequestBody PlayerConnection playerConnection) {
        if (playerConnection.getPlayerConnectionId() != playerId) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(playerConnectionService.updatePlayerConnection(playerConnection));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayerConnection(@PathVariable long playerId) {
        if (playerConnectionService.getPlayerConnectionByPlayerId(playerId) != null) {
            playerConnectionService.deletePlayerConnection(playerId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
