package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.communication.GameState;

public interface GameService {
    Game createGame(Game game);
    Game updateGame(Game game);
    Game getGame(Game game);
    Game convertToGame(GameState gameState);
    GameState convertToGameState(Game game);
}
