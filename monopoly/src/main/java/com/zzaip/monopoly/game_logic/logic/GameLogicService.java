package com.zzaip.monopoly.game_logic.logic;

import com.zzaip.monopoly.dto.GameDTO;

public interface GameLogicService {

    GameDTO hostGame(String myPlayerName);

    GameDTO joinGame(String myPlayerName, String hostURL);

    Long addPlayer(String playerName);

    GameDTO startGame();

    GameDTO endGame();

    GameDTO startTurn();

    void receiveGameUpdate(GameDTO gameDTO);

    GameDTO getActiveGameSnapshot();

    GameDTO buyHouse(int fieldNumber);

    GameDTO buyProperty();

    GameDTO endTurn();
}
