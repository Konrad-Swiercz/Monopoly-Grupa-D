package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;

public interface FieldService {
    Game onStand(Field field, Game game);
    boolean shouldGetBonus(int initialField);
    Field updateField(Field field);
    void deleteField(int id);
    Field createField(Field field);
    Field getFieldById(int id);
    Field getFieldByFieldNumber(int fieldNumber);
    StartField getStartField();
}
