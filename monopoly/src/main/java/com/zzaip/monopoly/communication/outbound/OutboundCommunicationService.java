package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.dto.GameDTO;

import java.util.List;

public interface OutboundCommunicationService {

    void sendGameUpdate(GameDTO gameDTO);

    void sendGameUpdate(GameDTO gameDTO, String playerURL);

    void sendGameUpdate(GameDTO gameDTO, List<String> playerURL);

    GameDTO joinGame(String playerURL, String myURL, String myName);

    boolean connectionCheck(String playerURL, String myURL);
}
