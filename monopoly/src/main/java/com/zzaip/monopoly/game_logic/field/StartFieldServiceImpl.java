package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;

public class StartFieldServiceImpl extends GeneralFieldService implements StartFieldService {
    @Override
    public Game onStand(Field field, Game game) {
        return game;
    }

    @Override
    public float calculateBonus(StartField startField) {
        return startField.getBonus();
    }
}
