package com.zzaip.monopoly.communication.connection;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PlayerConnectionServiceImplTest {

    @InjectMocks
    private PlayerConnectionServiceImpl playerConnectionService;

    @Mock
    private PlayerConnectionRepository playerConnectionRepository;

    @Test
    void getPlayerConnections_ReturnsAllPlayerConnections() {
        // Arrange
        List<PlayerConnection> expectedConnections = Arrays.asList(
                PlayerConnection.builder().playerConnectionId(1L).playerName("Player 1").playerURL("url1").isActive(true).build(),
                PlayerConnection.builder().playerConnectionId(2L).playerName("Player 2").playerURL("url2").isActive(false).build()
        );
        when(playerConnectionRepository.findAll()).thenReturn(expectedConnections);

        // Act
        List<PlayerConnection> actualConnections = playerConnectionService.getPlayerConnections();

        // Assert
        assertEquals(expectedConnections, actualConnections);
    }

    @Test
    void getPlayerConnectionByPlayerId_ExistingPlayerId_ReturnsPlayerConnection() {
        // Arrange
        long playerId = 1L;
        PlayerConnection expectedConnection = PlayerConnection.builder().playerConnectionId(playerId).playerName("Player 1").playerURL("url1").isActive(true).build();
        when(playerConnectionRepository.findById(playerId)).thenReturn(Optional.of(expectedConnection));

        // Act
        PlayerConnection actualConnection = playerConnectionService.getPlayerConnectionByPlayerId(playerId);

        // Assert
        assertEquals(expectedConnection, actualConnection);
    }

    @Test
    void getPlayerConnectionByPlayerId_NonExistingPlayerId_ThrowsException() {
        // Arrange
        long playerId = 1L;
        when(playerConnectionRepository.findById(playerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerConnectionService.getPlayerConnectionByPlayerId(playerId));
    }

    @Test
    void createPlayerConnection_ValidPlayerConnection_ReturnsCreatedPlayerConnection() {
        // Arrange
        PlayerConnection playerConnection = PlayerConnection.builder().playerName("Player 1").playerURL("url1").isActive(true).build();
        when(playerConnectionRepository.save(playerConnection)).thenReturn(playerConnection);

        // Act
        PlayerConnection createdConnection = playerConnectionService.createPlayerConnection(playerConnection);

        // Assert
        assertEquals(playerConnection, createdConnection);
    }

    @Test
    void updatePlayerConnection_ExistingPlayerConnection_ReturnsUpdatedPlayerConnection() {
        // Arrange
        long playerConnectionId = 1L;
        PlayerConnection playerConnection = PlayerConnection.builder().playerConnectionId(playerConnectionId).playerName("Player 1").playerURL("url1").isActive(true).build();
        when(playerConnectionRepository.findById(playerConnectionId)).thenReturn(Optional.of(playerConnection));
        when(playerConnectionRepository.save(playerConnection)).thenReturn(playerConnection);

        // Act
        PlayerConnection updatedConnection = playerConnectionService.updatePlayerConnection(playerConnection);

        // Assert
        assertEquals(playerConnection, updatedConnection);
    }

    @Test
    void updatePlayerConnection_NonExistingPlayerConnection_ThrowsException() {
        // Arrange
        PlayerConnection playerConnection = PlayerConnection.builder().playerConnectionId(1L).playerName("Player 1").playerURL("url1").isActive(true).build();
        when(playerConnectionRepository.findById(playerConnection.getPlayerConnectionId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playerConnectionService.updatePlayerConnection(playerConnection));
    }

    @Test
    void deletePlayerConnection_ExistingPlayerConnection_DeletesPlayerConnection() {
        // Arrange
        long playerConnectionId = 1L;
        when(playerConnectionRepository.existsById(playerConnectionId)).thenReturn(true);

        // Act
        playerConnectionService.deletePlayerConnection(playerConnectionId);

        // Assert
        Mockito.verify(playerConnectionRepository, times(1)).deleteById(playerConnectionId);
    }

    @Test
    void deletePlayerConnection_NonExistingPlayerConnection_DoesNotDeletePlayerConnection() {
        // Arrange
        long playerConnectionId = 1L;
        when(playerConnectionRepository.existsById(playerConnectionId)).thenReturn(false);

        // Act
        playerConnectionService.deletePlayerConnection(playerConnectionId);

        // Assert
        Mockito.verify(playerConnectionRepository, times(0)).deleteById(playerConnectionId);
    }
}