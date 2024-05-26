package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import com.zzaip.monopoly.communication.dto.GameRoomDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.game_logic.game.Game;

import java.util.List;

public interface GameRoomService {
    List<GameRoom> getAllGameRooms();

    GameRoom getGameRoomById(long gameRoomId);

    GameRoom createGameRoom(GameRoom gameRoom);

    GameRoom createNewEmptyGameRoom(boolean isOwner, int playersLimit);
    GameRoom createNewEmptyGameRoom(PlayerConnectionDTO myPlayer, boolean isOwner, int playersLimit);
    GameRoom updateGameRoom(GameRoom gameRoom);

    GameRoom getActiveGameRoom();

    List<String> getActivePlayersURLs();


    void deleteGameRoom(long gameRoomId);

    GameRoom joinGameRoom(GameRoom gameRoom, PlayerConnection playerConnection);

    GameRoom leaveGameRoom(GameRoom gameRoom, PlayerConnection playerConnection);

    GameRoomDTO convertToDTO(GameRoom gameRoom);
    GameRoom createGameRoomFromDTO(GameRoomDTO gameRoomDTO);

    List<String> getURLsToUpdate(GameRoom gameRoom);
}
