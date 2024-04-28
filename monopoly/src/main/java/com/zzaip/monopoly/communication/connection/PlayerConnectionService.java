package com.zzaip.monopoly.communication.connection;

import java.util.List;

public interface PlayerConnectionService {

    List<PlayerConnection> getPlayerConnections();

    PlayerConnection getPlayerConnectionByPlayerId(long playerId);

    PlayerConnection createPlayerConnection(PlayerConnection playerConnection);

    PlayerConnection updatePlayerConnection(PlayerConnection playerConnection);

    void deletePlayerConnection(long playerConnectionId);
}
