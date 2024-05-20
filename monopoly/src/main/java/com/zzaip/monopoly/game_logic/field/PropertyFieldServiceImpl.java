package com.zzaip.monopoly.game_logic.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyFieldServiceImpl extends GeneralFieldService {

    @Autowired
    private FieldRepository repository;

    @Override
    public void onStand(Field field) {
        // Implement logic
    }

    @Override
    public void hasWalkedThroughStart(int initial, int endField) {
        // Implement logic
    }

    @Override
    public Field updateField(Field field) {
        return repository.save(field);
    }

    @Override
    public void deleteField(int id) {
        repository.deleteById(id);
    }

    @Override
    public Field createField(Field field) {
        return repository.save(field);
    }

    @Override
    public Field getField(int id) {
        return repository.findById(id).orElse(null);
    }

    public float calculateRent(PropertyField field) {
        // Implement rent calculation
        return 0;
    }
}

