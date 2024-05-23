package com.zzaip.monopoly.game_logic.game.parser;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.start.StartField;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.player.Player;

import java.util.List;

public interface GameParser {
    Game parseGameFromConfig(List<Field> fields, Player player, StartField startField);
}
