package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.field.start.StartField;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BaseFieldServiceImpl implements BaseFieldService {
    private final FieldRepository fieldRepository;

    @Autowired
    public BaseFieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

//    @Override
//    public boolean shouldGetBonus(int initialField) {
//        // Implement shared logic for handling passing the start field
//        if (endField < initialField) {
//            // Logic for passing start field
//        }
//    }

    // Abstract methods for specific field types

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
    public Field getFieldById(int id) {
        return fieldRepository.findById(id).orElse(null);
    }

    @Override
    public Field getFieldByFieldNumber(int fieldNumber) {
        return fieldRepository.findFieldByFieldNumber(fieldNumber);
    }

    @Override
    public StartField getStartField() {
        List<Field> foundFields = getFieldsByFieldType(FieldType.START);
        if (foundFields.size() == 1) {
            return (StartField) foundFields.get(0);
        } else {
            throw new RuntimeException("Illegal amount of Start Fields: " +
                    foundFields.size());
        }
    }

    @Override
    public Game handlePassThroughStartField(Game game, Field initialField, Field landingField) {
        Player currentPlayer = game.getCurrentPlayer();
        StartField startField = getStartField();
        if (hasPassedStartField(initialField, landingField, startField)) {
            currentPlayer.setPlayerBalance(currentPlayer.getPlayerBalance() + startField.getBonus());
        }
        return game;
    }

    @Override
    public List<Field> getFieldsByFieldType(FieldType fieldType) {
        return fieldRepository.findFieldsByFieldType(fieldType);
    }

    protected boolean hasPassedStartField(Field initialField, Field landingField, StartField startField) {
        int initialFieldNumber = initialField.getFieldNumber();
        int landingFieldNumber = landingField.getFieldNumber();
        int startFieldNumber = startField.getFieldNumber();

        if (landingFieldNumber == startFieldNumber) {
            return true;
        }

        if (initialFieldNumber <= landingFieldNumber) {
            // no wrap-around
            return initialFieldNumber < startFieldNumber && landingFieldNumber >= startFieldNumber;
        } else {
            // wrap-around case
            return initialFieldNumber < startFieldNumber || landingFieldNumber >= startFieldNumber;
        }
    }



}
