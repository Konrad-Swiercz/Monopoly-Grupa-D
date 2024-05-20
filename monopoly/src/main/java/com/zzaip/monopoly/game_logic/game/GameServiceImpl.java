package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.communication.GameState;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    private final PlayerService playerService;
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(PlayerService playerService, GameRepository gameRepository) {
        this.playerService = playerService;
        this.gameRepository = gameRepository;
    }

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
    public Game convertToGame(GameState gameState) {
        Game game = new Game();
        game.setGameId(gameState.getGameId());
        game.setStatus(gameState.getStatus());
        game.setRoundCount(gameState.getRoundCount());
        game.setPlayers(playerService.getPlayersByNames(gameState.getPlayers()));
        game.setPlayersQueue(gameState.getPlayersQueue());
        game.setPlayersIsActive(gameState.getPlayersIsActive());
        game.setCurrentPlayer(playerService.findByName(gameState.getCurrentPlayer()));
        game.setBoard(convertToFieldList(gameState.getPropertyOwnership(), gameState.getPropertyHouses()));
        return game;
    }

    @Override
    public GameState convertToGameState(Game game) {
        GameState gameState = new GameState();
        gameState.setGameId(game.getGameId());
        gameState.setStatus(game.getStatus());
        gameState.setRoundCount(game.getRoundCount());
        gameState.setPlayers(game.getPlayers().stream().map(Player::getPlayerName).toList());
        gameState.setPlayersQueue(game.getPlayersQueue());
        gameState.setPlayersIsActive(game.getPlayersIsActive());
        gameState.setCurrentPlayer(game.getCurrentPlayer().getPlayerName());
        gameState.setPropertyOwnership(convertToPropertyOwnershipMap(game.getBoard()));
        gameState.setPropertyHouses(convertToPropertyHousesMap(game.getBoard()));
        return gameState;
    }

    private List<Field> convertToFieldList(Map<Integer, String> propertyOwnership, Map<Integer, Integer> propertyHouses) {
        // Implement conversion logic from GameState property maps to Field list
        return null;
    }

    private Map<Integer, String> convertToPropertyOwnershipMap(List<Field> board) {
        // Implement conversion logic from Field list to GameState propertyOwnership map
        return null;
    }

    private Map<Integer, Integer> convertToPropertyHousesMap(List<Field> board) {
        // Implement conversion logic from Field list to GameState propertyHouses map
        return null;
    }
}
