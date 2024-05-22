package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.dto.GameDTO;

public interface InboundCommunicationService {

    void receiveGameUpdate(GameDTO gameDTO);

    GameDTO addPlayer(String playerName, String playerURL);

    boolean connectionCheck(String playerURL);
}
