package com.zzaip.monopoly.game_logic.field.start;

import com.zzaip.monopoly.game_logic.field.CrudFieldServiceImpl;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldRepository;
import com.zzaip.monopoly.game_logic.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartFieldServiceImplImpl extends CrudFieldServiceImpl implements StartFieldService {
    @Autowired
    public StartFieldServiceImplImpl(FieldRepository fieldRepository) {
        super(fieldRepository);
    }

    @Override
    public Game onStand(Field field, Game game) {
        return game;
    }

    @Override
    public float calculateBonus(StartField startField) {
        return startField.getBonus();
    }
}
