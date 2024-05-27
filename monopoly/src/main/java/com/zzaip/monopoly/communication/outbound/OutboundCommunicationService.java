package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.communication.dto.GameRoomDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.dto.GameDTO;

import java.util.List;

public interface OutboundCommunicationService {

    void sendGameUpdate(GameDTO gameDTO);

    void sendGameUpdate(GameDTO gameDTO, String playerURL);

    void sendGameUpdate(GameDTO gameDTO, List<String> playerURL);

//    void sendGameRoomUpdate(GameRoomDTO gameRoomDTO, List<String> playerURL);
    void sendPlayerConnectionUpdate(PlayerConnectionDTO playerConnectionDTO, List<String> playerURL);

    GameDTO joinGame(String playerURL, String myURL, String myName);

    boolean connectionCheck(String playerURL, String myURL);

    void cleanup();
}
