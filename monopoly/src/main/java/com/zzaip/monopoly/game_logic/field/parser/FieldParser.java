package com.zzaip.monopoly.game_logic.field.parser;

import com.zzaip.monopoly.game_logic.field.Field;

import java.util.List;

public interface FieldParser {
    /**
     * use the board.xml resource to initialize the fields
     * @return initialized fields object based on board.xml config
     */
    List<Field> parseFieldsFromConfig();
}
