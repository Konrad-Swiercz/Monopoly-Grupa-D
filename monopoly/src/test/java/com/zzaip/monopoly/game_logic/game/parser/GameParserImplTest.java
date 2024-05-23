package com.zzaip.monopoly.game_logic.game.parser;

import com.zzaip.monopoly.game_logic.field.Field;
import com.zzaip.monopoly.game_logic.field.FieldType;
import com.zzaip.monopoly.game_logic.field.start.StartField;
import com.zzaip.monopoly.game_logic.game.Game;
import com.zzaip.monopoly.game_logic.game.GameStatus;
import com.zzaip.monopoly.game_logic.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GameParserImplTest {

    @Mock
    private GameDefaultsParser gameDefaultsParser;

    @InjectMocks
    private GameParserImpl gameParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void parseGameFromConfig() {
        List<Field> fields = Collections.emptyList();
        Player player = new Player("TestPlayer");
        StartField startField = new StartField(0, "Start", FieldType.START, 200);

        when(gameDefaultsParser.parseRoundLimit()).thenReturn(50);

        Game game = gameParser.parseGameFromConfig(fields, player, startField);

        assertEquals(50, game.getRoundLimit());
        assertEquals(1, game.getRoundCount());
        assertEquals(GameStatus.NOT_STARTED, game.getStatus());
        assertEquals(player, game.getCurrentPlayer());
        assertEquals(List.of(player), game.getPlayers());
        assertEquals(List.of(player.getPlayerName()), game.getPlayersQueue());
        assertEquals(fields, game.getBoard());
        assertEquals(startField, game.getStartField());
    }
}
