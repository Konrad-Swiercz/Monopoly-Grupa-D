package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.communication.GameState;

public interface OutboundCommunicationService {

    void sendGameUpdate(GameState gameStatus);

    void joinGame(String playerURL);

    void connectionCheck(String playerURL);
}
