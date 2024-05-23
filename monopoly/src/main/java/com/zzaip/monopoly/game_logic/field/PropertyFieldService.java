package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;

public interface PropertyFieldService extends FieldService {
    float calculateTotalWorth(PropertyField propertyField);
    Game buyProperty(PropertyField propertyField, Game game);
    Game buyHouse(PropertyField propertyField, Game game);
}
