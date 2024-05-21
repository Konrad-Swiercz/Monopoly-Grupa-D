package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.communication.GameState;
import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.player.Player;

import java.util.List;

public interface GameService {
    Game createGame(Game game);
    Game updateGame(Game game);
    Game getGame(Game game);
    Game getActiveGame();
    GameDTO convertToGameDTO(Game game);
    Game initializeGame(List<Field> fields, Player player);
}
