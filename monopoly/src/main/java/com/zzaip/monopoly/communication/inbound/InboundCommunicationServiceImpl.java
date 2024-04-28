package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.communication.GameState;
import com.zzaip.monopoly.communication.connection.PlayerConnection;
import com.zzaip.monopoly.communication.connection.PlayerConnectionService;
import com.zzaip.monopoly.communication.game_room.GameRoom;
import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.outbound.OutboundCommunicationService;
import com.zzaip.monopoly.game_logic.GameLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundCommunicationServiceImpl implements InboundCommunicationService{

    private final PlayerConnectionService playerConnectionService;
    private final OutboundCommunicationService outboundCommunicationService;
    private final GameRoomService gameRoomService;
    private final GameLogicService gameLogicService;
    @Override
    public void receiveGameUpdate(GameState gameStatus) {
        gameLogicService.receiveGameUpdate(gameStatus);
    }

    @Override
    public GameState addPlayer(String playerName, String playerURL) {
        // fetch the active game
        GameRoom gameRoom = gameRoomService.getActiveGameRoom();
        if (gameRoom == null) {
            throw new RuntimeException("No active game found");
        }
        List<String> hostsToUpdate = gameRoom.getConnectedPlayers().stream()
                .map(PlayerConnection::getPlayerURL)
                .toList();
        // add the player to the game logic
        Long playerId = gameLogicService.joinGame(playerName, playerURL);

        // create a new player connection record
        PlayerConnection playerConnection = PlayerConnection.builder()
                .playerId(playerId)
                .playerName(playerName)
                .playerURL(playerURL)
                .isActive(true)
                .build();
        playerConnectionService.createPlayerConnection(playerConnection);

        // update the game room
        gameRoomService.joinGameRoom(gameRoom, playerConnection);

        // if the app instance is host, update the remaining players
        GameState gameState = gameLogicService.getGameState();
        if (gameRoom.isOwner()) {
            // update the game state of the remaining players
            outboundCommunicationService.sendGameUpdate(gameState, hostsToUpdate);
        }

        return gameLogicService.getGameState();
    }

    @Override
    public boolean connectionCheck(String playerURL) {
        // fetch the active game
        GameRoom gameRoom = gameRoomService.getActiveGameRoom();
        if (gameRoom == null) {
            return false;
        }
        // return true if playerURL is in URLS of players connected to the active game
        return  gameRoom.getConnectedPlayers().stream()
                .map(PlayerConnection::getPlayerURL)
                .toList()
                .contains(playerURL);
    }
}
