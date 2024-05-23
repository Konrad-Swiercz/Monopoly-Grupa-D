package com.zzaip.monopoly.game_logic.logic;

import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.outbound.OutboundCommunicationService;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.game_logic.field.*;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.game.GameService;
import com.zzaip.monopoly.game_logic.game.GameStatus;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerParser;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Random;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameLogicServiceImpl implements GameLogicService {
    private final OutboundCommunicationService outboundCommunicationService;
    private final GameRoomService gameRoomService;
    private final PlayerService playerService;
    private final CrudFieldService crudFieldService;
    private final FieldServiceRegistry fieldServiceRegistry;
    private final FieldParser fieldParser;
    private final PlayerParser playerParser;
    private final GameService gameService;

    @Override
    public GameDTO hostGame(String myPlayerName) {
        Game game = gameService.getActiveGame();
        if (game != null) {
            throw new RuntimeException("Game already exists with status: " + game.getStatus() +
                    "\nPrevious game not finished!");
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
        // TODO: implement
        // set Game status to FINISHED
        // send update to all participants
        return getActiveGameSnapshot();
    }

    @Override
    public GameDTO startTurn() {
        Game game = gameService.getStartedGame();
        if (game == null) {
            throw new RuntimeException("No started games found");
        }
        if (gameService.isMyTurn(game)) {
            Player myPlayer = game.getCurrentPlayer();
            int dice = roll() + roll();
            Field initialField = crudFieldService.getFieldByFieldNumber(myPlayer.getPlayerPosition());
            Field landingField = gameService.getLandingField(game, initialField, dice);
            myPlayer.setPlayerPosition(landingField.getFieldNumber());
            playerService.updatePlayer(myPlayer);
            FieldService concreteFieldService = fieldServiceRegistry.getService(landingField.getFieldType());
            if (concreteFieldService != null) {
                //TODO: collect money start field

                // updates the game depending on the field type and field details
                concreteFieldService.onStand(landingField, game);
                // update the game record
                gameService.updateGame(game);
            }
        } else {
            throw new RuntimeException("It is not your turn.");
        }
        return getActiveGameSnapshot();
    }

    @Override
    public void receiveGameUpdate(GameDTO gameDTO) {
        gameService.updateActiveGame(gameDTO);
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

    private int roll() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}


