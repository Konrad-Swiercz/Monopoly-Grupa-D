package com.zzaip.monopoly.game_logic.field.start;

import com.zzaip.monopoly.game_logic.field.FieldService;

public interface StartFieldService extends FieldService {
    float calculateBonus(StartField startField);
}
