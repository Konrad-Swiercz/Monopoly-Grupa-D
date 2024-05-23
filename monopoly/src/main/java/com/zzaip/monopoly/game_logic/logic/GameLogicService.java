package com.zzaip.monopoly.game_logic.logic;

import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.game_logic.game.Game;

public interface GameLogicService {

    GameDTO hostGame(String myPlayerName);

    GameDTO joinGame(String myPlayerName, String hostURL);

    Long addPlayer(String playerName);

    GameDTO startGame();

    GameDTO endGame();

    GameDTO startTurn();

    void receiveGameUpdate(GameDTO gameDTO);

    GameDTO getActiveGameSnapshot();

    GameDTO buyHouse(Game game);

    GameDTO buyProperty(Game game);

    GameDTO endTurn(Game game);
}
