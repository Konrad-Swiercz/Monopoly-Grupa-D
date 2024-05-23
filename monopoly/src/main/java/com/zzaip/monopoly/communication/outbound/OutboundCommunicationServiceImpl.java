package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.inbound.JoinGameRequest;
import com.zzaip.monopoly.dto.GameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboundCommunicationServiceImpl implements OutboundCommunicationService{
    private final GameRoomService gameRoomService;

    @Override
    public void sendGameUpdate(GameDTO gameStatus) {
        List<String> urlsToUpdate = gameRoomService.getActivePlayersURLs();
        for (String url:
             urlsToUpdate) {
            sendGameUpdate(gameStatus, url);
        }
    }

    @Override
    public void sendGameUpdate(GameDTO gameStatus, String playerURL) {
        ExternalApiConsumer consumer = new ExternalApiConsumer(playerURL);
        consumer.callReceiveGameUpdate(gameStatus);
    }

    @Override
    public void sendGameUpdate(GameDTO gameStatus, List<String> playerURLs) {
        for (String url:
                playerURLs) {
            sendGameUpdate(gameStatus, url);
        }
    }

    @Override
    public GameDTO joinGame(String playerURL, String myURL, String myName) {
        ExternalApiConsumer consumer = new ExternalApiConsumer(playerURL);
        JoinGameRequest joinGameRequest = new JoinGameRequest(myName, myURL);
        return consumer.callAddPlayer(joinGameRequest);
    }

    @Override
    public boolean connectionCheck(String playerURL, String myURL) {
        ExternalApiConsumer consumer = new ExternalApiConsumer(playerURL);
        return consumer.callConnectionCheck(myURL);
    }
}
