package com.zzaip.monopoly.game_logic.field.start;

import com.zzaip.monopoly.game_logic.field.BaseFieldServiceImpl;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldRepository;
import com.zzaip.monopoly.game_logic.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartFieldServiceImpl extends BaseFieldServiceImpl implements StartFieldService {
    @Autowired
    public StartFieldServiceImpl(FieldRepository fieldRepository) {
        super(fieldRepository);
    }

    @Override
    public Game onStand(Field landingField, Field initialField, Game game) {
        handlePassThroughStartField(game, initialField, landingField);
        return game;
    }

    @Override
    public float calculateBonus(StartField startField) {
        return startField.getBonus();
    }
}
