package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;

public interface FieldService {
    /**
     * Update the Game object depending on the field type of the Field the player stood on.
     * @param field
     * @param game
     * @return
     */
    Game onStand(Field field, Game game);

//    boolean shouldGetBonus(int initialField);
    Field updateField(Field field);
    void deleteField(int id);
    Field createField(Field field);
    Field getFieldById(int id);
    Field getFieldByFieldNumber(int fieldNumber);
    StartField getStartField();
}
