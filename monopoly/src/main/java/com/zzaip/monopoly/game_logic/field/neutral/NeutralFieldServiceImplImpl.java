package com.zzaip.monopoly.game_logic.field.neutral;

import com.zzaip.monopoly.game_logic.field.CrudFieldServiceImpl;
import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldRepository;
import com.zzaip.monopoly.game_logic.field.FieldService;
import com.zzaip.monopoly.game_logic.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeutralFieldServiceImplImpl extends CrudFieldServiceImpl implements FieldService {

    @Autowired
    public NeutralFieldServiceImplImpl(FieldRepository fieldRepository) {
        super(fieldRepository);
    }

    @Override
    public Game onStand(Field field, Game game) {
        return game;
    }
}
