package com.zzaip.monopoly.game_logic.field;

public interface FieldService {
    void onStand(Field field);
    void hasWalkedThroughStart(int initial, int endField);
    Field updateField(Field field);
    void deleteField(int id);
    Field createField(Field field);
    Field getField(int id);
}
