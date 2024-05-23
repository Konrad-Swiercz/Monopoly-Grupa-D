package com.zzaip.monopoly.game_logic.field.property;

import com.zzaip.monopoly.game_logic.field.FieldService;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.player.Player;

import java.util.List;

public interface PropertyFieldService extends FieldService {
    float calculateTotalWorth(PropertyField propertyField);
    List<PropertyField> getPlayerProperties(Player player);
    Game buyProperty(PropertyField propertyField, Game game);
    Game buyHouse(PropertyField propertyField, Game game);
}
