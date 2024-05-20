package com.zzaip.monopoly.game_logic.field;

public abstract class GeneralFieldService implements FieldService {

    @Override
    public void hasWalkedThroughStart(int initial, int endField) {
        // Implement shared logic
        if (endField < initial) {
            // Logic for passing start field
        }
    }

    // Abstract methods for specific field types
    @Override
    public abstract void onStand(Field field);

    @Override
    public abstract Field updateField(Field field);

    @Override
    public abstract void deleteField(int id);

    @Override
    public abstract Field createField(Field field);

    @Override
    public abstract Field getField(int id);
}
