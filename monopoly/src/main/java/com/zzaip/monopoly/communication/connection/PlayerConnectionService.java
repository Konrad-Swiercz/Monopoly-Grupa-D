package com.zzaip.monopoly.communication.connection;

import com.zzaip.monopoly.communication.dto.GameRoomDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.communication.game_room.GameRoom;

import java.util.List;

public interface PlayerConnectionService {

    List<PlayerConnection> getPlayerConnections();

    PlayerConnection getPlayerConnectionByPlayerId(long playerId);

    PlayerConnection createPlayerConnection(PlayerConnection playerConnection);

    PlayerConnection updatePlayerConnection(PlayerConnection playerConnection);

    void deletePlayerConnection(long playerConnectionId);
    PlayerConnectionDTO convertToDTO(PlayerConnection playerConnection);
    PlayerConnection convertToPlayerConnection(PlayerConnectionDTO playerConnectionDTO);

    List<PlayerConnection> createPlayerConnectionsFromDTOs(List<PlayerConnectionDTO> playerConnectionDTOS);
}
