package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {
    // TODO: implement joining an existing game room
    // TODO: implement creating a new game room

    private final GameRoomRepository gameRoomRepository;

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
    public GameRoom createNewEmptyGameRoom() {
        // TODO: implement
        GameRoom gameRoom = GameRoom.builder()
                .roomName("gameroom")
                .isOwner(true)
                .isActive(true)
                .playersLimit(4)
                .build();
        return gameRoom;
    }

    @Override
    public GameRoom updateGameRoom(GameRoom gameRoom) {
        // Check if game room exists before updating
        if (gameRoomRepository.findById(gameRoom.getGameRoomId()).isPresent()) {
            return gameRoomRepository.save(gameRoom);
        } else {
            throw new RuntimeException("Game room does not exist");
        }
    }

    @Override
    public GameRoom getActiveGameRoom() {
        List<GameRoom> activeGameRooms = gameRoomRepository.findGameRoomsByActiveTrue();
        int countActiveGames = activeGameRooms.size();
        if (countActiveGames == 1) {
            return activeGameRooms.get(0);
        } else if (countActiveGames == 0) {
            return null;
        } else {
            throw new RuntimeException(
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
        throw new RuntimeException("Cannot join - Game room is full");
    }

    @Override
    public GameRoom leaveGameRoom(GameRoom gameRoom, PlayerConnection playerConnection) {
        gameRoom.getConnectedPlayers().remove(playerConnection);
        return updateGameRoom(gameRoom);
    }
}