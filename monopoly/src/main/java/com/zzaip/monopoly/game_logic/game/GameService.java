package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.player.Player;

import java.util.List;

public interface GameService {
    Game createGame(Game game);
    Game updateGame(Game game);

    void deleteGame(Game game);
    Game getGame(Game game);

    /**
     * Add player to player list and to the queue. The method persists (saves) the updated game.
     * Throws RuntimeException if a player with the same name already exists.
     *
     * @param game Game object to be modified
     * @param player Player object to be added
     * @return an updated Game object
     */
    Game addPlayer(Game game, Player player);

    /**
     * Retrieve the Game with status STARTED if it is available.
     * In other case retrieve a Game with NOT_STARTED status.
     * If the latter is not available, return a Game with FINISHED status.
     * @return Active (STARTED or NOT_STARTED) Game or null.
     */
    Game getGame();

    /**
     * Retrieve the Game with status STARTED if it is available.
     * In other case retrieve a Game with NOT_STARTED status.
     * If the latter is not available, return null.
     * @return Active (STARTED or NOT_STARTED) Game or null.
     */
    Game getActiveGame();
    /**
     * Retrieve the Game with status NOT_STARTED if it is available.
     * If the latter is not available, return null.
     * @return a pending Game or null.
     */
    Game getStartedGame();
    /**
     * Retrieve the Game with status STARTED if it is available.
     * If the latter is not available, return null.
     * @return a started Game or null.
     */
    Game getPendingGame();
    /**
     * Retrieve the Game with status FINISHED if it is available.
     * If the latter is not available, return null.
     * @return a started Game or null.
     */
    Game getFinishedGame();

    Game updateActiveGame(GameDTO gameDTO);
    GameDTO convertToGameDTO(Game game);
    Game initializeGame(List<Field> fields, Player player);

    boolean isMyTurn(Game game);

    boolean hasCurrentPlayerMoved(Game game);

    Game finishTurn(Game game);

    Field getLandingField(Game game, Field field, int dice);
    Game handleGameOver(Game game);

    Game updateNextPlayer(Game game);
}
