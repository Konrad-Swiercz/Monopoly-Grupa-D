package com.zzaip.monopoly.game_logic.game;

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

import java.util.List;
import java.util.Objects;
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
    public Game addPlayer(Game game, Player player) {
        // Check if a player with the same name already exists
        for (Player existingPlayer : game.getPlayers()) {
            if (existingPlayer.getPlayerName().equals(player.getPlayerName())) {
                throw new RuntimeException("Player with name " + player.getPlayerName() + " already exists");
            }
        }
        game.getPlayers().add(player);
        game.getPlayersQueue().add(player.getPlayerName());
        return gameRepository.save(game);
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
            Field field = fieldService.getFieldById(propertyFieldDTO.getFieldNumber());
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
                .roundLimit(1000) // TODO: take from config
                .players(List.of(player))
                .playersQueue(List.of(player.getPlayerName()))
                .currentPlayer(player)
                .myPlayerName(player.getPlayerName())
                .board(fields)
                .startField(startFieldService.getStartField())
                .build();
    }

    @Override
    public boolean isMyTurn(Game game) {
        return Objects.equals(game.getCurrentPlayer().getPlayerName(), game.getMyPlayerName());
    }

    @Override
    public Field getLandingField(Game game, Field initialField, int dice) {
        int boardSize = game.getBoard().size();
        int initialFielndNumber = initialField.getFieldNumber();
        int landingFieldNumber = (initialFielndNumber + dice) % boardSize;
        return fieldService.getFieldByFieldNumber(landingFieldNumber);
    }


    /**
     * updates the game object if the game over condition is met.
     * The game is over if the current round is greater than round limit
     * or if all players but one have lost
     * @param game the game that is going to be checked on the game over condition and updated.
     * @return updated game
     */
    @Override
    public Game handleGameOver(Game game) {
        // TODO: update the winner
        // TODO: implement a private method for calculating total wealth
        // Check if the current round is greater than the round limit
        if (game.getRoundCount() > game.getRoundLimit()) {
            game.setStatus(GameStatus.FINISHED);
            return game;
        }

        // Check if all players but one have lost
        List<Player> players = game.getPlayers();
        long activePlayersCount = players.stream().filter(player -> !player.isHasLost()).count();
        if (activePlayersCount <= 1) {
            game.setStatus(GameStatus.FINISHED);
        }

        return gameRepository.save(game);
    }

    @Override
    public Game getStartedGame() {
        List<Game> startedGames = gameRepository.findGamesByStatus(GameStatus.STARTED);
        int gamesFound = startedGames.size();
        if (gamesFound == 1) {
            return startedGames.get(0);
        } else if (gamesFound == 0) {
            return null;
        }
        throw new RuntimeException("Illegal amount of active games");
    }

    @Override
    public Game getPendingGame() {
        List<Game> startedGames = gameRepository.findGamesByStatus(GameStatus.NOT_STARTED);
        int gamesFound = startedGames.size();
        if (gamesFound == 1) {
            return startedGames.get(0);
        } else if (gamesFound == 0) {
            return null;
        }
        throw new RuntimeException("Illegal amount of pending games");
    }

    /**
     * finds a winner of the finished game
     * @param game a finished game
     * @return a game with updated winner field
     */
    private Game findWinner(Game game) {
        if (game.getStatus() == GameStatus.FINISHED) {
            List<Player> players = game.getPlayers();
            List<Player> activePlayers = players.stream().filter(player -> !player.isHasLost()).toList();
            if (activePlayers.size() < 1) {
                throw new RuntimeException("No active players left in the game - cannot pick the winner");
            }
            if (activePlayers.size() == 1) {
                Player winner = activePlayers.get(0);
                game.setWinnerPlayerName(winner.getPlayerName());
            } else {
                // TODO: for each player calculate total wealth and find the Player with max wealth
            }

        }
        // TODO: implement
        return null;
    }
}
