package com.zzaip.monopoly.game_logic.field;

public interface FieldService {
    void onStand(Field field);
    boolean shouldGetBonus(int initialField);
    Field updateField(Field field);
    void deleteField(int id);
    Field createField(Field field);
    Field getField(int id);
    StartField getStartField();
}
