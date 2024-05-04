package com.zzaip.monopoly.game_logic.logic;

import com.zzaip.monopoly.communication.GameState;
import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.outbound.OutboundCommunicationService;
import com.zzaip.monopoly.game_logic.field.FieldService;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.game.GameService;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class GameLogicServiceImpl implements GameLogicService{
    private final OutboundCommunicationService outboundCommunicationService;
    private final GameRoomService gameRoomService;
//    private final PlayerService playerService;
//    private final FieldService fieldService;
    private final GameService gameService;

    @Override
    public void hostGame() {
        gameRoomService.createNewEmptyGameRoom();
    }

    @Override
    public void joinGame(String myPlayerName, String hostURL) {
        String myURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        GameState gameState = outboundCommunicationService.joinGame(hostURL, myURL, myPlayerName);
        Game game = gameService.convertToGame(gameState);
        gameService.createGame(game);
    }

    @Override
    public Long addPlayer(String playerName, String playerURL) {
        return null;
    }


    @Override
    public void startGame(Game game) {

    }

    @Override
    public void endGame(Game game) {

    }

    @Override
    public void startTurn(Game game) {

    }

    @Override
    public void receiveGameUpdate(GameState gameState) {

    }

    @Override
    public GameState getGameState() {
        return null;
    }

    @Override
    public void buyHouse(Game game) {

    }

    @Override
    public void buyProperty(Game game) {

    }

    @Override
    public void endTurn(Game game) {

    }
}
