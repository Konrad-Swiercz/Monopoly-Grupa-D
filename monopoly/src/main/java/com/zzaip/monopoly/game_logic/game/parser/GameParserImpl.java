package com.zzaip.monopoly.game_logic.game.parser;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.start.StartField;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.game.GameStatus;
import com.zzaip.monopoly.game_logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameParserImpl implements GameParser {

    private final GameDefaultsParser gameDefaultsParser;

    @Autowired
    public GameParserImpl(GameDefaultsParser gameDefaultsParser) {
        this.gameDefaultsParser = gameDefaultsParser;
    }

    @Override
    public Game parseGameFromConfig(List<Field> fields, Player player, StartField startField) {
        int roundLimitConfig = gameDefaultsParser.parseRoundLimit();
        int playerLimitConfig = gameDefaultsParser.parsePlayerLimit();

        return Game.builder()
                .status(GameStatus.NOT_STARTED)
                .roundCount(1)
                .roundLimit(roundLimitConfig)
                .playerLimit(playerLimitConfig)
                .players(new ArrayList<>(List.of(player)))
                .playersQueue(new ArrayList<>(List.of(player.getPlayerName())))
                .currentPlayer(player)
                .myPlayerName(player.getPlayerName())
                .board(new ArrayList<>(fields))
                .startField(startField)
                .build();
    }
}
