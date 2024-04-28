package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.communication.GameState;

import java.util.List;

public interface OutboundCommunicationService {

    void sendGameUpdate(GameState gameStatus);

    void sendGameUpdate(GameState gameStatus, String playerURL);

    void sendGameUpdate(GameState gameStatus, List<String> playerURL);

    GameState joinGame(String playerURL, String myURL, String myName);

    boolean connectionCheck(String playerURL, String myURL);
}
