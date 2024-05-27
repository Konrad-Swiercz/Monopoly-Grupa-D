package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import com.zzaip.monopoly.communication.connection.PlayerConnectionService;
import com.zzaip.monopoly.communication.dto.GameRoomDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.communication.exceptions.CommunicationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {

    private final GameRoomRepository gameRoomRepository;
    private final PlayerConnectionService playerConnectionService;

    @Override
    public List<GameRoom> getAllGameRooms() {
        return (List<GameRoom>) gameRoomRepository.findAll();
    }

    @Override
    public GameRoom getGameRoomById(long gameRoomId) {
        return gameRoomRepository.findById(gameRoomId).orElse(null);
    }

    @Override
    public GameRoom createGameRoom(GameRoom gameRoom) {
        return gameRoomRepository.save(gameRoom);
    }

    @Override
    public void deleteAllGameRooms() {
        gameRoomRepository.deleteAll();
    }

    @Override
    public GameRoom createNewEmptyGameRoom(boolean isOwner, int playersLimit) {
        GameRoom gameRoom = GameRoom.builder()
                .roomName("game room")
                .isOwner(isOwner)
                .isActive(true)
                .playersLimit(playersLimit)
                .build();
        return gameRoomRepository.save(gameRoom);
    }

    @Override
    public GameRoom createNewEmptyGameRoom(PlayerConnectionDTO myPlayer, boolean isOwner, int playersLimit) {
        PlayerConnection myPlayerConnection = playerConnectionService.convertToPlayerConnection(myPlayer);
        myPlayerConnection = playerConnectionService.createPlayerConnection(myPlayerConnection);
        GameRoom gameRoom = GameRoom.builder()
                .roomName("game room")
                .isOwner(isOwner)
                .isActive(true)
                .playersLimit(playersLimit)
                .connectedPlayers(List.of(myPlayerConnection))
                .build();
        return gameRoomRepository.save(gameRoom);
    }

    @Override
    public GameRoom updateGameRoom(GameRoom gameRoom) {
        // Check if game room exists before updating
        if (gameRoomRepository.findById(gameRoom.getGameRoomId()).isPresent()) {
            return gameRoomRepository.save(gameRoom);
        } else {
            throw new CommunicationError("Game room does not exist");
        }
    }

    @Override
    public GameRoom getActiveGameRoom() {
        List<GameRoom> activeGameRooms = gameRoomRepository.findGameRoomsByIsActiveTrue();
        int countActiveGames = activeGameRooms.size();
        if (countActiveGames == 1) {
            return activeGameRooms.get(0);
        } else if (countActiveGames == 0) {
            return null;
        } else {
            throw new CommunicationError(
                    String.format("Found %s active games, expected 1", activeGameRooms)
            );
        }
    }

    @Override
    public List<String> getActivePlayersURLs() {
        GameRoom activeGameRoom = getActiveGameRoom();
        return activeGameRoom.getConnectedPlayers().stream()
                .filter(PlayerConnection::isActive)
                .map(PlayerConnection::getPlayerURL)
                .toList();
    }

    @Override
    public void deleteGameRoom(long gameRoomId) {
        gameRoomRepository.deleteById(gameRoomId);
    }

    @Override
    public GameRoom joinGameRoom(GameRoom gameRoom, PlayerConnection playerConnection) {
        if (gameRoom.getConnectedPlayers().size() < gameRoom.getPlayersLimit()) {
            gameRoom.getConnectedPlayers().add(playerConnection);
            return updateGameRoom(gameRoom);
        }
        throw new CommunicationError("Cannot join - Game room is full");
    }

    @Override
    public GameRoom leaveGameRoom(GameRoom gameRoom, PlayerConnection playerConnection) {
        gameRoom.getConnectedPlayers().remove(playerConnection);
        return updateGameRoom(gameRoom);
    }

    @Override
    public GameRoomDTO convertToDTO(GameRoom gameRoom) {
        List<PlayerConnectionDTO> playerConnectionDTOs = gameRoom.getConnectedPlayers().stream()
                .map(playerConnectionService::convertToDTO)
                .collect(Collectors.toList());

        return new GameRoomDTO(
                gameRoom.getRoomName(),
                gameRoom.isActive(),
                gameRoom.getPlayersLimit(),
                playerConnectionDTOs
        );
    }

    @Override
    public GameRoom createGameRoomFromDTO(GameRoomDTO gameRoomDTO) {
        List<PlayerConnection> playerConnections = playerConnectionService.createPlayerConnectionsFromDTOs(
                gameRoomDTO.getConnectedPlayers()
        );

        GameRoom gameRoom = GameRoom.builder()
                .roomName(gameRoomDTO.getRoomName())
                .isActive(gameRoomDTO.isActive())
                .isOwner(false)
                .playersLimit(gameRoomDTO.getPlayersLimit())
                .connectedPlayers(playerConnections)
                .build();
        return gameRoomRepository.save(gameRoom);
    }

    /**
     * return all player URLs from the Game Room that are not own URL (we do not update our own game)
     * @param gameRoom the active game room
     * @return list of player URLs that should be updated
     */
    @Override
    public List<String> getURLsToUpdate(GameRoom gameRoom) {
        String myURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return gameRoom.getConnectedPlayers().stream()
                .map(PlayerConnection::getPlayerURL)
                .filter(playerURL -> !playerURL.equals(myURL))
                .collect(Collectors.toList());
    }

}