package com.zzaip.monopoly.game_logic.field;

public interface CrudFieldService {
    Field updateField(Field field);
    void deleteField(int id);
    Field createField(Field field);
    Field getFieldById(int id);
    Field getFieldByFieldNumber(int fieldNumber);
    StartField getStartField();
}
