package com.zzaip.monopoly.game_logic.player.parser;

import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.parser.PlayerDefaultsParser;
import com.zzaip.monopoly.game_logic.player.parser.PlayerParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerParserImpl implements PlayerParser {

    private final PlayerDefaultsParser playerDefaultsParser;

    @Autowired
    public PlayerParserImpl(PlayerDefaultsParser playerDefaultsParser) {
        this.playerDefaultsParser = playerDefaultsParser;
    }

    @Override
    public Player parsePlayerFromConfig(String playerName) {
        int playerPosition = playerDefaultsParser.parsePlayerPosition();
        float playerBalance = playerDefaultsParser.parsePlayerBalance();

        return new Player(playerName, playerPosition, playerBalance);
    }
}
