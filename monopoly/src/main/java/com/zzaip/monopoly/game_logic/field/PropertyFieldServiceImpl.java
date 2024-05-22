package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyFieldServiceImpl extends GeneralFieldService {

    @Autowired
    private FieldRepository repository;

    @Override
    public Game onStand(Field field, Game game) {
        // Implement logic
        return null;
    }

    @Override
    public boolean shouldGetBonus(int initialField) {
        // Implement logic
        return false;
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
    public Field getFieldById(int id) {
        return repository.findById(id).orElse(null);
    }

    public float calculateRent(PropertyField field) {
        // Implement rent calculation
        return 0;
    }
}

