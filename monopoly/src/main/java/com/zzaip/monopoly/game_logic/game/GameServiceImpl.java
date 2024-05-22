package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.communication.GameState;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.dto.PlayerDTO;
import com.zzaip.monopoly.dto.PropertyFieldDTO;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldService;
import com.zzaip.monopoly.game_logic.field.PropertyField;
import com.zzaip.monopoly.game_logic.field.StartFieldServiceImpl;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final PlayerService playerService;
    private final GameRepository gameRepository;
    private final StartFieldServiceImpl startFieldService;
    private final FieldService fieldService;


    @Override
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game updateGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game getGame(Game game) {
        return gameRepository.findById(game.getGameId()).orElse(null);
    }

    @Override
    public Game getActiveGame() {
        Game game = getStartedGame();
        if (game == null) {
            game = getPendingGame();
        }
        return game;
    }

    @Override
    public Game updateActiveGame(GameDTO gameDTO) {
        Game game = getActiveGame();
        if (game == null) {
            throw new RuntimeException("No active game found");
        }

        game.setStatus(GameStatus.valueOf(gameDTO.getStatus()));
        game.setRoundCount(gameDTO.getRoundCount());

        for (PlayerDTO playerDTO : gameDTO.getPlayers()) {
            Player player = playerService.findByName(playerDTO.getPlayerName());
            if (player != null) {
                player.setPlayerPosition(playerDTO.getPlayerPosition());
                player.setPlayerBalance(playerDTO.getPlayerBalance());
                player.setJailTurns(playerDTO.getJailTurns());
                player.setHasLost(playerDTO.isHasLost());
                playerService.updatePlayer(player);
            }
        }

        game.setPlayersQueue(gameDTO.getPlayersQueue());

        Player currentPlayer = playerService.findByName(gameDTO.getCurrentPlayerName());
        game.setCurrentPlayer(currentPlayer);

        for (PropertyFieldDTO propertyFieldDTO : gameDTO.getProperties()) {
            Field field = fieldService.getField(propertyFieldDTO.getFieldNumber());
            if (field instanceof PropertyField propertyField) {
                propertyField.setOwner(playerService.findByName(propertyFieldDTO.getOwnerPlayerName()));
                propertyField.setHouseCount(propertyFieldDTO.getHouseCount());
                fieldService.updateField(propertyField);
            } else {
                throw new RuntimeException("Fields are not in sync between players");
            }
        }
        return gameRepository.save(game);
    }

    @Override
    public GameDTO convertToGameDTO(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGameId(game.getGameId());
        gameDTO.setStatus(game.getStatus().name());
        gameDTO.setRoundCount(game.getRoundCount());
        gameDTO.setPlayers(game.getPlayers().stream().map(player -> {
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setPlayerName(player.getPlayerName());
            playerDTO.setPlayerPosition(player.getPlayerPosition());
            playerDTO.setPlayerBalance(player.getPlayerBalance());
            playerDTO.setJailTurns(player.getJailTurns());
            playerDTO.setHasLost(player.isHasLost());
            return playerDTO;
        }).collect(Collectors.toList()));
        gameDTO.setPlayersQueue(game.getPlayersQueue());
        gameDTO.setCurrentPlayerName(game.getCurrentPlayer().getPlayerName());
        gameDTO.setProperties(game.getBoard().stream()
                .filter(field -> field instanceof PropertyField)
                .map(field -> {
                    PropertyFieldDTO dto = new PropertyFieldDTO();
                    PropertyField propertyField = (PropertyField) field;
                    dto.setFieldNumber(propertyField.getFieldNumber());
                    dto.setOwnerPlayerName(propertyField.getOwner().getPlayerName());
                    dto.setHouseCount(propertyField.getHouseCount());
                    return dto;
                }).collect(Collectors.toList()));
        return gameDTO;
    }


    @Override
    public Game initializeGame(List<Field> fields, Player player) {
        return Game.builder()
                .status(GameStatus.NOT_STARTED)
                .roundCount(1)
                .players(List.of(player))
                .playersQueue(List.of(player.getPlayerName()))
                .currentPlayer(player)
                .board(fields)
                .startField(startFieldService.getStartField())
                .build();
    }

    private Game getStartedGame() {
        List<Game> startedGames = gameRepository.findGamesByStatus(GameStatus.STARTED);
        int gamesFound = startedGames.size();
        if (gamesFound == 1) {
            return startedGames.get(0);
        } else if (gamesFound == 0) {
            return null;
        }
        throw new RuntimeException("Illegal amount of active games");
    }

    private Game getPendingGame() {
        List<Game> startedGames = gameRepository.findGamesByStatus(GameStatus.NOT_STARTED);
        int gamesFound = startedGames.size();
        if (gamesFound == 1) {
            return startedGames.get(0);
        } else if (gamesFound == 0) {
            return null;
        }
        throw new RuntimeException("Illegal amount of pending games");
    }
}
