package com.zzaip.monopoly.game_logic.field.neutral;

import com.zzaip.monopoly.game_logic.field.BaseFieldServiceImpl;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldRepository;
import com.zzaip.monopoly.game_logic.field.FieldService;
import com.zzaip.monopoly.game_logic.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeutralFieldServiceImpl extends BaseFieldServiceImpl implements FieldService {

    @Autowired
    public NeutralFieldServiceImpl(FieldRepository fieldRepository) {
        super(fieldRepository);
    }

    @Override
    public Game onStand(Field landingField, Field initialField, Game game) {
        handlePassThroughStartField(game, initialField, landingField);
        return game;
    }
}
