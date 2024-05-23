package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.field.start.StartField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class CrudFieldServiceImpl implements CrudFieldService {
    private final FieldRepository fieldRepository;

    @Autowired
    public CrudFieldServiceImpl(FieldRepository fieldRepository) {
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

    protected List<Field> getFieldsByFieldType(FieldType fieldType) {
        return fieldRepository.findFieldsByFieldType(fieldType);
    }

}
