package com.zzaip.monopoly.communication.outbound;

import com.zzaip.monopoly.communication.connection.PlayerConnectionService;
import com.zzaip.monopoly.communication.dto.GameRoomDTO;
import com.zzaip.monopoly.communication.dto.JoinGameDTO;
import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.communication.game_room.GameRoom;
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
    private final PlayerConnectionService playerConnectionService;

    @Override
    public void sendGameUpdate(GameDTO gameStatus) {
        GameRoom activeGameRoom = gameRoomService.getActiveGameRoom();
        List<String> urlsToUpdate = gameRoomService.getURLsToUpdate(activeGameRoom);
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

    public void sendPlayerConnectionUpdate(PlayerConnectionDTO playerConnectionDTO, String playerURL) {
        ExternalApiConsumer consumer = new ExternalApiConsumer(playerURL);
        consumer.callReceivePlayerConnection(playerConnectionDTO);
    }

        /**
         * Call the receivePLayerConnection endpoint for each url.
         * @param playerConnectionDTO connection details
         * @param playerURLs list of urls that should have the api-s called to maintain the player connections
         */
    @Override
    public void sendPlayerConnectionUpdate(PlayerConnectionDTO playerConnectionDTO, List<String> playerURLs) {
        for (String url:
                playerURLs) {
            sendPlayerConnectionUpdate(playerConnectionDTO, url);
        }
    }

//    public void sendGameRoomUpdate(GameRoomDTO gameRoomDTO, String playerURL) {
//        ExternalApiConsumer consumer = new ExternalApiConsumer(playerURL);
//        consumer.callReceiveGameUpdate(gameRoomDTO);
//    }
//
//    @Override
//    public void sendGameRoomUpdate(GameRoomDTO gameRoomDTO, List<String> playerURLs) {
//        for (String url:
//                playerURLs) {
//            sendGameRoomUpdate(gameRoomDTO, url);
//        }
//    }

    @Override
    public GameDTO joinGame(String hostURL, String myURL, String myName) {
        ExternalApiConsumer consumer = new ExternalApiConsumer(hostURL);
        JoinGameRequest joinGameRequest = new JoinGameRequest(myName, myURL);
        JoinGameDTO joinGameDTO = consumer.callAddPlayer(joinGameRequest);
        gameRoomService.createGameRoomFromDTO(joinGameDTO.getGameRoom());
        return joinGameDTO.getGame();
    }

    @Override
    public boolean connectionCheck(String playerURL, String myURL) {
        ExternalApiConsumer consumer = new ExternalApiConsumer(playerURL);
        return consumer.callConnectionCheck(myURL);
    }

    @Override
    public void cleanup() {
        playerConnectionService.deleteAllPlayerConnections();
        gameRoomService.deleteAllGameRooms();
    }
}
