package com.zzaip.monopoly.game_logic.field;

public interface FieldService {
    void onStand();
    boolean hasWalkedThroughStart(int startField, int endField);

}
