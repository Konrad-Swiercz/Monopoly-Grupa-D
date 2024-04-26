package com.zzaip.monopoly.communication.connection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerConnectionServiceImpl implements PlayerConnectionService {

    private final PlayerConnectionRepository playerConnectionRepository;

    @Override
    public List<PlayerConnection> getPlayerConnections() {
        return (List<PlayerConnection>) playerConnectionRepository.findAll();
    }

    @Override
    public PlayerConnection getPlayerConnectionByPlayerId(long playerId) {
        return playerConnectionRepository.findById(playerId);
    }

    @Override
    @Transactional // Mark method for transaction management
    public PlayerConnection createPlayerConnection(String player, String playerURL) {
        PlayerConnection playerConnection = new PlayerConnection(player, playerURL);
        playerConnectionRepository.save(playerConnection);
        return playerConnection;
    }

    @Override
    @Transactional
    public PlayerConnection updatePlayerConnection(PlayerConnection playerConnection) {
        PlayerConnection existingConnection = playerConnectionRepository.findById(playerConnection.getPlayerConnectionId()).orElse(null);
        if (existingConnection != null) {
            existingConnection.setPlayer(playerConnection.getPlayer());
            existingConnection.setPlayerURL(playerConnection.getPlayerURL());
            existingConnection.setActive(playerConnection.isActive());
            playerConnectionRepository.save(existingConnection);
            return existingConnection;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deletePlayerConnection(int playerConnectionId) {
        playerConnectionRepository.deleteById(playerConnectionId);
    }
}

