package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.field.start.StartField;
import com.zzaip.monopoly.game_logic.game.Game;

import java.util.List;

public interface BaseFieldService {
    Field updateField(Field field);
    void deleteField(int id);

    void deleteAllFields();
    Field createField(Field field);
    List<Field> createFields(List<Field> fields);
    Field getFieldById(int id);
    Field getFieldByFieldNumber(int fieldNumber);
    List<Field> getFieldsByFieldType(FieldType fieldType);
    StartField getStartField();

    Game handlePassThroughStartField(Game game, Field initialField, Field landingField);

}
