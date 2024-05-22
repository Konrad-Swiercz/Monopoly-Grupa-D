package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.communication.connection.PlayerConnection;
import com.zzaip.monopoly.communication.connection.PlayerConnectionService;
import com.zzaip.monopoly.communication.game_room.GameRoom;
import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.outbound.OutboundCommunicationService;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.game_logic.logic.GameLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InboundCommunicationServiceImpl implements InboundCommunicationService{

    private final PlayerConnectionService playerConnectionService;
    private final OutboundCommunicationService outboundCommunicationService;
    private final GameRoomService gameRoomService;
    private final GameLogicService gameLogicService;
    @Override
    public void receiveGameUpdate(GameDTO gameDTO) {
        gameLogicService.receiveGameUpdate(gameDTO);
    }

    @Override
    public GameDTO addPlayer(String playerName, String playerURL) {
        // fetch the active game
        GameRoom gameRoom = gameRoomService.getActiveGameRoom();
        if (gameRoom == null) {
            throw new RuntimeException("No active game found");
        }
        List<String> hostsToUpdate = gameRoom.getConnectedPlayers().stream()
                .map(PlayerConnection::getPlayerURL)
                .toList();
        // add the player to the game logic
        Long playerId = gameLogicService.addPlayer(playerName, playerURL);

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
        GameDTO gameDTO = gameLogicService.getActiveGameSnapshot();
        if (gameRoom.isOwner()) {
            // update the game state of the remaining players
            outboundCommunicationService.sendGameUpdate(gameDTO, hostsToUpdate);
        }

        return gameDTO;
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
