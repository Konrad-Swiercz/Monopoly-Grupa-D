package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;

public interface StartFieldService extends FieldService{
    float calculateBonus(StartField startField);
}
