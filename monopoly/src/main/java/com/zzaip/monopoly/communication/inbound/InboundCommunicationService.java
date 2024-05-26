package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.communication.dto.GameRoomDTO;
import com.zzaip.monopoly.communication.dto.JoinGameDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.dto.GameDTO;

public interface InboundCommunicationService {

    void receiveGameUpdate(GameDTO gameDTO);

    JoinGameDTO addPlayerToCurrentGame(String playerName, String playerURL);

    void receivePlayerConnection(PlayerConnectionDTO playerConnectionDTO);

    boolean connectionCheck(String playerURL);
}
