package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {

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
    public GameRoom updateGameRoom(GameRoom gameRoom) {
        // Check if game room exists before updating
        if (gameRoomRepository.findById(gameRoom.getGameRoomId()).isPresent()) {
            return gameRoomRepository.save(gameRoom);
        } else {
            throw new RuntimeException("Game room does not exist");
        }
    }

    @Override
    public void deleteGameRoom(long gameRoomId) {
        gameRoomRepository.deleteById(gameRoomId);
    }

    @Override
    public void joinGameRoom(GameRoom gameRoom, PlayerConnection playerConnection) {
        gameRoom.getConnectedPlayers().add(playerConnection);
        gameRoomRepository.save(gameRoom);
    }

    @Override
    public void leaveGameRoom(GameRoom gameRoom, PlayerConnection playerConnection) {
        gameRoom.getConnectedPlayers().remove(playerConnection);
        gameRoomRepository.save(gameRoom);
    }
}