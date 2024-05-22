package com.zzaip.monopoly.game_logic.field;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Integer> {
    Field findFieldByFieldNumber(int fieldNumber);
    List<Field> findFieldsByFieldType(FieldType fieldType);
}