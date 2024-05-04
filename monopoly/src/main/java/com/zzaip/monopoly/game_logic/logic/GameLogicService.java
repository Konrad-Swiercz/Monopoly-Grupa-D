package com.zzaip.monopoly.game_logic.logic;

import com.zzaip.monopoly.communication.GameState;
import com.zzaip.monopoly.game_logic.game.Game;

public interface GameLogicService {

    void hostGame();

    void joinGame(String myPlayerName, String hostURL);

    Long addPlayer(String playerName, String playerURL);

    void startGame(Game game);

    void endGame(Game game);

    void startTurn(Game game);

    void receiveGameUpdate(GameState gameState);

    GameState getGameState();

    void buyHouse(Game game);

    void buyProperty(Game game);

    void endTurn(Game game);
}
