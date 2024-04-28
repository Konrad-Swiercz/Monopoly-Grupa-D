package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.communication.GameState;

public interface InboundCommunicationService {

    void receiveGameUpdate(GameState gameStatus);

    GameState addPlayer(String playerName, String playerURL);

    boolean connectionCheck(String playerURL);
}
