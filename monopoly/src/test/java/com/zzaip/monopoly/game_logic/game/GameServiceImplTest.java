package com.zzaip.monopoly.game_logic.game;

import com.zzaip.monopoly.dto.GameDTO;
import com.zzaip.monopoly.dto.PlayerDTO;
import com.zzaip.monopoly.dto.PropertyFieldDTO;
import com.zzaip.monopoly.game_logic.field.BaseFieldService;
import com.zzaip.monopoly.game_logic.field.property.PropertyField;
import com.zzaip.monopoly.game_logic.field.property.PropertyFieldService;
import com.zzaip.monopoly.game_logic.field.start.StartFieldService;
import com.zzaip.monopoly.game_logic.player.Player;
import com.zzaip.monopoly.game_logic.player.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private StartFieldService startFieldService;

    @Mock
    private BaseFieldService baseFieldService;

    @Mock
    private PropertyFieldService propertyFieldService;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPlayer() {
    }

    @Test
    void getActiveGame() {
        Game startedGame = new Game();
        startedGame.setStatus(GameStatus.STARTED);

        when(gameRepository.findGamesByStatus(GameStatus.STARTED)).thenReturn(Collections.singletonList(startedGame));

        Game activeGame = gameService.getActiveGame();
        assertEquals(GameStatus.STARTED, activeGame.getStatus());

        verify(gameRepository).findGamesByStatus(GameStatus.STARTED);
        verify(gameRepository, never()).findGamesByStatus(GameStatus.NOT_STARTED);
    }

    @Test
    void updateActiveGame() {
        Game game = new Game();
        game.setPlayers(Collections.singletonList(new Player()));
        game.setBoard(Collections.singletonList(new PropertyField()));
        game.setStatus(GameStatus.STARTED);

        GameDTO gameDTO = new GameDTO();
        gameDTO.setStatus("STARTED");
        gameDTO.setRoundCount(1);
        gameDTO.setPlayers(Collections.singletonList(new PlayerDTO()));
        gameDTO.setProperties(Collections.singletonList(new PropertyFieldDTO()));

        when(gameRepository.findGamesByStatus(GameStatus.STARTED)).thenReturn(Collections.singletonList(game));
        when(playerService.findByName(anyString())).thenReturn(new Player());
        when(baseFieldService.getFieldByFieldNumber(anyInt())).thenReturn(new PropertyField());

        gameService.updateActiveGame(gameDTO);

        verify(gameRepository).save(game);
        assertEquals(GameStatus.STARTED, game.getStatus());
    }

    @Test
    void convertToGameDTO() {
    }

    @Test
    void getLandingField() {
    }

    @Test
    void getStartedGame() {
        Game startedGame = new Game();
        startedGame.setStatus(GameStatus.STARTED);

        when(gameRepository.findGamesByStatus(GameStatus.STARTED)).thenReturn(Collections.singletonList(startedGame));

        Game result = gameService.getStartedGame();
        assertNotNull(result);
        assertEquals(GameStatus.STARTED, result.getStatus());
    }

    @Test
    void getPendingGame() {
        Game pendingGame = new Game();
        pendingGame.setStatus(GameStatus.NOT_STARTED);

        when(gameRepository.findGamesByStatus(GameStatus.NOT_STARTED)).thenReturn(Collections.singletonList(pendingGame));

        Game result = gameService.getPendingGame();
        assertNotNull(result);
        assertEquals(GameStatus.NOT_STARTED, result.getStatus());
    }
}
