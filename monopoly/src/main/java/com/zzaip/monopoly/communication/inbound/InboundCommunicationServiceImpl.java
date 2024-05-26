package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.communication.dto.GameRoomDTO;
import com.zzaip.monopoly.communication.dto.JoinGameDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.communication.connection.PlayerConnection;
import com.zzaip.monopoly.communication.connection.PlayerConnectionService;
import com.zzaip.monopoly.communication.game_room.GameRoom;
import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.outbound.OutboundCommunicationService;
import com.zzaip.monopoly.game_logic.logic.GameLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public JoinGameDTO addPlayerToCurrentGame(String playerName, String playerURL) {
        // fetch the active game
        GameRoom gameRoom = gameRoomService.getActiveGameRoom();
        if (gameRoom == null) {
            throw new RuntimeException("No active game found");
        }
        if (!gameRoom.isOwner()) {
            throw new RuntimeException("This is not the game host / owner");
        }
        List<String> urlsToUpdate = gameRoomService.getURLsToUpdate(gameRoom);
        // add the player to the game logic
        Long playerId = gameLogicService.addPlayer(playerName);

        // create a new player connection record
        PlayerConnection playerConnection = PlayerConnection.builder()
                .playerId(playerId)
                .playerName(playerName)
                .playerURL(playerURL)
                .isActive(true)
                .build();
        playerConnectionService.createPlayerConnection(playerConnection);

        // update the game room
        gameRoom = gameRoomService.joinGameRoom(gameRoom, playerConnection);

        // if the app instance is host, update the remaining players
        GameDTO gameDTO = gameLogicService.getActiveGameSnapshot();
        GameRoomDTO gameRoomDTO = gameRoomService.convertToDTO(gameRoom);
        PlayerConnectionDTO playerConnectionDTO = playerConnectionService.convertToDTO(playerConnection);
        // update the game state of the remaining players
        outboundCommunicationService.sendGameUpdate(gameDTO, urlsToUpdate);
        // update the game rooms of the remaining players
        outboundCommunicationService.sendPlayerConnectionUpdate(playerConnectionDTO, urlsToUpdate);
        return new JoinGameDTO(gameRoomDTO, gameDTO);
    }

    /**
     * Convert player connection DTO to a PlayerConnection object and search
     * if Player Connection with such player name exists in the active Game Room.
     * If it exists, update it.
     * If it doesn't exist - add it to the list and persist the connection.
     * @param playerConnectionDTO incoming player connection information
     */
    @Override
    public void receivePlayerConnection(PlayerConnectionDTO playerConnectionDTO) {
        GameRoom gameRoom = gameRoomService.getActiveGameRoom();
        if (gameRoom == null) {
            throw new RuntimeException("No active Game Room found");
        }

        PlayerConnection playerConnection = playerConnectionService.convertToPlayerConnection(playerConnectionDTO);
        Optional<PlayerConnection> existingConnection = gameRoom.getConnectedPlayers().stream()
                .filter(conn -> conn.getPlayerName().equals(playerConnection.getPlayerName()))
                .findFirst();

        if (existingConnection.isPresent()) {
            PlayerConnection existingPlayerConnection = existingConnection.get();
            existingPlayerConnection.setPlayerURL(playerConnection.getPlayerURL());
            existingPlayerConnection.setActive(playerConnection.isActive());
            playerConnectionService.updatePlayerConnection(existingPlayerConnection);
        } else {
            gameRoom.getConnectedPlayers().add(playerConnection);
            playerConnectionService.createPlayerConnection(playerConnection);
        }

        gameRoomService.updateGameRoom(gameRoom);
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
