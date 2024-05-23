package com.zzaip.monopoly.game_logic.player.parser;

import com.zzaip.monopoly.game_logic.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PlayerParserImplTest {

    @Mock
    private PlayerDefaultsParser playerDefaultsParser;

    @InjectMocks
    private PlayerParserImpl playerParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void parsePlayerFromConfig() {
        String playerName = "TestPlayer";

        when(playerDefaultsParser.parsePlayerPosition()).thenReturn(0);
        when(playerDefaultsParser.parsePlayerBalance()).thenReturn(1500.0f);

        Player player = playerParser.parsePlayerFromConfig(playerName);

        assertEquals(playerName, player.getPlayerName());
        assertEquals(0, player.getPlayerPosition());
        assertEquals(1500.0f, player.getPlayerBalance());
    }
}
