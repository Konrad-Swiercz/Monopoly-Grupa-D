package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;

public class NeutralFieldServiceImpl extends GeneralFieldService{
    @Override
    public Game onStand(Field field, Game game) {
        return game;
    }
}
