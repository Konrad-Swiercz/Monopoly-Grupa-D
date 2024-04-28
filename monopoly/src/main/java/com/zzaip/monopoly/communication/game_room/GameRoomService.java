package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import com.zzaip.monopoly.game_logic.game.Game;

import java.util.List;

public interface GameRoomService {
    List<GameRoom> getAllGameRooms();

    GameRoom getGameRoomById(long gameRoomId);

    GameRoom createGameRoom(GameRoom gameRoom);

    GameRoom updateGameRoom(GameRoom gameRoom);

    GameRoom getActiveGameRoom();

    void deleteGameRoom(long gameRoomId);

    GameRoom joinGameRoom(GameRoom gameRoom, PlayerConnection playerConnection);

    GameRoom leaveGameRoom(GameRoom gameRoom, PlayerConnection playerConnection);
}
