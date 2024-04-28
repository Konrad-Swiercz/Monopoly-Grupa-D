package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.communication.GameState;

import java.util.List;

public interface OutboundCommunicationService {

    void sendGameUpdate(GameState gameStatus);

    void sendGameUpdate(GameState gameStatus, String playerURL);

    void sendGameUpdate(GameState gameStatus, List<String> playerURL);

    void joinGame(String playerURL);

    void connectionCheck(String playerURL);
}
