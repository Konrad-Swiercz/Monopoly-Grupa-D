package com.zzaip.monopoly.game_logic.logic;

import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.outbound.OutboundCommunicationService;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldParser;
import com.zzaip.monopoly.game_logic.field.FieldService;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.game.GameService;
import com.zzaip.monopoly.game_logic.game.GameStatus;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerParser;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameLogicServiceImpl implements GameLogicService {
    private final OutboundCommunicationService outboundCommunicationService;
    private final GameRoomService gameRoomService;
    private final PlayerService playerService;
    private final FieldService fieldService;
    private final FieldParser fieldParser;
    private final PlayerParser playerParser;
    private final GameService gameService;

    @Override
    public GameDTO hostGame(String myPlayerName) {
        Game game = gameService.getActiveGame();
        if (game != null) {
            throw new RuntimeException("Game already exists." +
                    " Previous game not finished!");
        }
        game = initializeDefaultGame(myPlayerName);
        gameService.createGame(game);
        gameRoomService.createNewEmptyGameRoom(); // TODO: check if need to add myplayer to game room
        return getActiveGameSnapshot();
    }

    @Override
    public GameDTO joinGame(String myPlayerName, String hostURL) {
        String myURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        GameDTO receivedGameDTO = outboundCommunicationService.joinGame(hostURL, myURL, myPlayerName);
        Game game = initializeDefaultGame(myPlayerName);
        gameService.createGame(game);
        gameService.updateActiveGame(receivedGameDTO);
        return getActiveGameSnapshot();
    }

    @Override
    public Long addPlayer(String playerName) {
        Game game = gameService.getPendingGame();
        if (game == null) {
            throw new RuntimeException("no pending (NOT_STARTED) games found");
        }
        Player player = playerParser.parsePlayerFromConfig(playerName);
        player = playerService.createPlayer(player);
        gameService.addPlayer(game, player);
        return player.getPlayerId();
    }

    @Override
    public GameDTO startGame() {
        Game game = gameService.getPendingGame();
        if (game == null) {
            throw new RuntimeException("no pending (NOT_STARTED) games found");
        }
        game.setStatus(GameStatus.STARTED);
        gameService.updateGame(game);
        return getActiveGameSnapshot();
    }

    @Override
    public GameDTO endGame() {
        return getActiveGameSnapshot();
    }

    @Override
    public GameDTO startTurn() {
        return getActiveGameSnapshot();
    }

    @Override
    public void receiveGameUpdate(GameDTO gameDTO) {

    }

    @Override
    public GameDTO getActiveGameSnapshot() {
        return gameService.convertToGameDTO(gameService.getActiveGame());
    }

    @Override
    public GameDTO buyHouse(Game game) {
        return getActiveGameSnapshot();
    }

    @Override
    public GameDTO buyProperty(Game game) {
        return getActiveGameSnapshot();
    }

    @Override
    public GameDTO endTurn(Game game) {
        return getActiveGameSnapshot();
    }

    private Game initializeDefaultGame(String myPlayerName) {
        List<Field> fields = fieldParser.parseFieldsFromConfig();
        Player myPlayer = playerParser.parsePlayerFromConfig(myPlayerName);
        return gameService.initializeGame(fields, myPlayer);
    }
}


