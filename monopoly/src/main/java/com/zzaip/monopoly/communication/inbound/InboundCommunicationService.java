package com.zzaip.monopoly.communication.inbound;

import com.zzaip.monopoly.communication.GameState;

public interface InboundCommunicationService {

    void receiveGameUpdate(GameState gameStatus);

    void addPlayer(JoinGameRequest joinGameRequest);

    void connectionCheck();
}
