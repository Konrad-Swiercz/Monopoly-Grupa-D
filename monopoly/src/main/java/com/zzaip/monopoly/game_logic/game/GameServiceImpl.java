package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.communication.GameState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService{
    @Override
    public Game createGame(Game game) {
        return null;
    }

    @Override
    public Game updateGame(Game game) {
        return null;
    }

    @Override
    public Game getGame(Game game) {
        return null;
    }

    @Override
    public Game convertToGame(GameState gameState) {
        return null;
    }

    @Override
    public GameState convertToGameState(Game game) {
        return null;
    }
}
