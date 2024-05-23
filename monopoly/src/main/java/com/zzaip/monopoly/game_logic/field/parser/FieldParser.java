package com.zzaip.monopoly.game_logic.field.parser;

import com.zzaip.monopoly.game_logic.field.Field;

import java.util.List;

public interface FieldParser {
    List<Field> parseFieldsFromConfig();
}
