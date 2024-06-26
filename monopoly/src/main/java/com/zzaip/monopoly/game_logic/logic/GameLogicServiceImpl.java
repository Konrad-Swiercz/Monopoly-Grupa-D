package com.zzaip.monopoly.game_logic.logic;

import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.communication.game_room.GameRoomService;
import com.zzaip.monopoly.communication.outbound.OutboundCommunicationService;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.dto.PropertyFieldDTO;
import com.zzaip.monopoly.game_logic.exceptions.GameLogicException;
import com.zzaip.monopoly.game_logic.field.*;
import com.zzaip.monopoly.game_logic.field.parser.FieldParser;
import com.zzaip.monopoly.game_logic.field.property.PropertyField;
import com.zzaip.monopoly.game_logic.field.property.PropertyFieldService;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.game.GameService;
import com.zzaip.monopoly.game_logic.game.GameStatus;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.parser.PlayerParser;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameLogicServiceImpl implements GameLogicService {
    private final OutboundCommunicationService outboundCommunicationService;
    private final GameRoomService gameRoomService;
    private final PlayerService playerService;
    private final BaseFieldService baseFieldService;
    private final FieldServiceRegistry fieldServiceRegistry;
    private final PropertyFieldService propertyFieldService;
    private final FieldParser fieldParser;
    private final PlayerParser playerParser;
    private final GameService gameService;

    @Override
    public GameDTO hostGame(String myPlayerName) {
        String myURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        prepareForNewGame();
        Game game = initializeDefaultGame(myPlayerName);
        gameService.createGame(game);
        PlayerConnectionDTO myPlayerConnDTO = new PlayerConnectionDTO(myPlayerName, myURL, true);
        gameRoomService.createNewEmptyGameRoom(myPlayerConnDTO, true, game.getPlayerLimit());
        return getActiveGameSnapshot();
    }

    @Override
    public GameDTO joinGame(String myPlayerName, String hostURL) {
        String myURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        prepareForNewGame();
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
            throw new GameLogicException("no pending (NOT_STARTED) games found");
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
            throw new GameLogicException("no pending (NOT_STARTED) games found");
        }

        // Set the position of all players to the start field number
        int startFieldNumber = game.getStartField().getFieldNumber();
        for (Player player : game.getPlayers()) {
            player.setPlayerPosition(startFieldNumber);
            playerService.updatePlayer(player);  // persist the updated player
        }

        game.setStatus(GameStatus.STARTED);
        gameService.updateGame(game);
        GameDTO snapshot = getActiveGameSnapshot();
        outboundCommunicationService.sendGameUpdate(snapshot);
        return snapshot;
    }


    @Override
    public GameDTO startTurn() {
        Game game = gameService.getStartedGame();
        if (game == null) {
            throw new GameLogicException("No started games found");
        }
        if (gameService.isMyTurn(game)) {
            if (gameService.hasCurrentPlayerMoved(game)) {
                throw new GameLogicException("Cannot move twice in the same turn. You can either buy the property," +
                        " buy a house or finish your turn");
            }
            Player myPlayer = game.getCurrentPlayer();
            int dice = roll() + roll();
            Field initialField = baseFieldService.getFieldByFieldNumber(myPlayer.getPlayerPosition());
            Field landingField = gameService.getLandingField(game, initialField, dice);
            myPlayer.setPlayerPosition(landingField.getFieldNumber());
            game.setPlayerHasMoved(true);
            playerService.updatePlayer(myPlayer);
            FieldService concreteFieldService = fieldServiceRegistry.getService(landingField.getFieldType());
            if (concreteFieldService != null) {
                // updates the game depending on the landing field type and its details
                // collects the pass-through-start bonus
                game = concreteFieldService.onStand(landingField, initialField, game);
                // update the game record
                gameService.updateGame(game);
                GameDTO snapshot = getActiveGameSnapshot();
                outboundCommunicationService.sendGameUpdate(snapshot);
                return snapshot;
            }
        } else {
            throw new GameLogicException("It is not your turn.");
        }
        return getActiveGameSnapshot();
    }

    @Override
    public void receiveGameUpdate(GameDTO gameDTO) {
        gameService.updateActiveGame(gameDTO);
    }

    @Override
    public GameDTO getActiveGameSnapshot() {
        return gameService.convertToGameDTO(gameService.getGame());
    }

    @Override
    public GameDTO buyHouse(int fieldNumber) {
        Game game = gameService.getStartedGame();
        if (game == null) {
            throw new GameLogicException("No started games found");
        }
        if (gameService.isMyTurn(game)) {
            Field field = baseFieldService.getFieldByFieldNumber(fieldNumber);
            if (field.getFieldType() == FieldType.PROPERTY) {
                PropertyField propertyField = (PropertyField) field;
                game = propertyFieldService.buyHouse(propertyField, game);
                gameService.updateGame(game);
                GameDTO snapshot = getActiveGameSnapshot();
                outboundCommunicationService.sendGameUpdate(snapshot);
                return snapshot;
            } else {
                throw new GameLogicException("not a property field");
            }
        } else {
            throw new GameLogicException("it is not your turn");
        }
    }

    @Override
    public GameDTO buyProperty() {
        Game game = gameService.getStartedGame();

        if (game == null) {
            throw new GameLogicException("No started games found");
        }
        if (gameService.isMyTurn(game)) {
            Player myPlayer = game.getCurrentPlayer();
            Field currentField = baseFieldService.getFieldByFieldNumber(myPlayer.getPlayerPosition());
            if (currentField.getFieldType() == FieldType.PROPERTY) {
                PropertyField propertyField = (PropertyField) currentField;
                game = propertyFieldService.buyProperty(propertyField, game);
                gameService.updateGame(game);
                GameDTO snapshot = getActiveGameSnapshot();
                outboundCommunicationService.sendGameUpdate(snapshot);
                return snapshot;
            } else {
                throw new GameLogicException("not a property field");
            }
        } else {
            throw new GameLogicException("it is not your turn");
        }
    }

    @Override
    public GameDTO endTurn() {
        Game game = gameService.getStartedGame();
        if (game == null) {
            throw new GameLogicException("No started games found");
        }
        if (gameService.isMyTurn(game)) {
            game = gameService.finishTurn(game);
            game = gameService.handleGameOver(game);
            if (game.getStatus() == GameStatus.FINISHED) {
                // END GAME SCENARIO
                gameService.updateGame(game);
                GameDTO snapshot = getActiveGameSnapshot();
                outboundCommunicationService.sendGameUpdate(snapshot);
                return snapshot;
            } else {
                // GAME CONTINUES.
                gameService.updateNextPlayer(game);
                GameDTO snapshot = getActiveGameSnapshot();
                outboundCommunicationService.sendGameUpdate(snapshot);
                return snapshot;
            }
        } else {
            throw new GameLogicException("it is not your turn");
        }
    }

    private Game initializeDefaultGame(String myPlayerName) {
        List<Field> fields = fieldParser.parseFieldsFromConfig();
        baseFieldService.createFields(fields);
        Player myPlayer = playerParser.parsePlayerFromConfig(myPlayerName);
        playerService.createPlayer(myPlayer);
        return gameService.initializeGame(new ArrayList<>(fields), myPlayer);
    }

    private int roll() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    private void cleanup(Game game) {
        baseFieldService.deleteAllFields();
        playerService.deleteAllPlayers();
        gameService.deleteGame(game);
        outboundCommunicationService.cleanup();
    }

    private void validateNoActiveGamesExist() {
        Game game = gameService.getActiveGame();
        if (game != null) {
            throw new GameLogicException("Game already exists with status: " + game.getStatus() +
                    "\nPrevious game not finished!");
        }
    }

    /**
     * perform the check for any active Game and
     * a cleanup for the finished game.
     * Throws an error if active game exists.
     * Removes the Game record if a Game with status
     * FINISHED was found.
     */
    private void prepareForNewGame() {
        validateNoActiveGamesExist();
        Game finishedGame = gameService.getFinishedGame();
        if (finishedGame != null) {
            cleanup(finishedGame);
        }
    }

    @Override
    public GameDTO updateField(PropertyFieldDTO propertyFieldDTO) {
        int fieldNumber = propertyFieldDTO.getFieldNumber();
        PropertyField field = (PropertyField) propertyFieldService.getFieldByFieldNumber(fieldNumber);
        Player player = playerService.findByName(propertyFieldDTO.getOwnerPlayerName());
        field.setOwner(player);
        field.setHouseCount(propertyFieldDTO.getHouseCount());
        baseFieldService.updateField(field);
        GameDTO snapshot = getActiveGameSnapshot();
        outboundCommunicationService.sendGameUpdate(snapshot);
        return snapshot;
    }

}


