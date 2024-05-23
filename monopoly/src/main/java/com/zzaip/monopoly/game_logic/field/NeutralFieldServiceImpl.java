package com.zzaip.monopoly.game_logic.field;

import com.zzaip.monopoly.game_logic.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeutralFieldServiceImpl extends GeneralFieldService implements FieldService {

    @Autowired
    public NeutralFieldServiceImpl(FieldRepository fieldRepository) {
        super(fieldRepository);
    }

    @Override
    public Game onStand(Field field, Game game) {
        return game;
    }
}
