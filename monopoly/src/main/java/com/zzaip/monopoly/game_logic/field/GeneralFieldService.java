package com.zzaip.monopoly.game_logic.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class GeneralFieldService implements FieldService {

    @Autowired
    private FieldRepository fieldRepository;

//    @Override
//    public boolean shouldGetBonus(int initialField) {
//        // Implement shared logic for handling passing the start field
//        if (endField < initialField) {
//            // Logic for passing start field
//        }
//    }

    // Abstract methods for specific field types
    @Override
    public abstract void onStand(Field field);

    @Override
    public Field updateField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public void deleteField(int id) {
        fieldRepository.deleteById(id);
    }

    @Override
    public Field createField(Field field) {
        return fieldRepository.save(field);
    }

    @Override
    public Field getField(int id) {
        return fieldRepository.findById(id).orElse(null);
    }
}
