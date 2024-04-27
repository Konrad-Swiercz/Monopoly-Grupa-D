package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;

import java.util.List;

public interface GameRoomService {
    List<GameRoom> getAllGameRooms();

    GameRoom getGameRoomById(long gameRoomId);

    GameRoom createGameRoom(GameRoom gameRoom);

    GameRoom updateGameRoom(GameRoom gameRoom);

    void deleteGameRoom(long gameRoomId);

    void joinGameRoom(GameRoom gameRoom, PlayerConnection playerConnection);

    void leaveGameRoom(GameRoom gameRoom, PlayerConnection playerConnection);
}
