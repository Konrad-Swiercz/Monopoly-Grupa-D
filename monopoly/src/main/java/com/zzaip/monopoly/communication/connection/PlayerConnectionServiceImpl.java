package com.zzaip.monopoly.communication.connection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<PlayerConnection> queriedConnection = playerConnectionRepository.findById(playerId);
        if (queriedConnection.isPresent()) {
            return queriedConnection.get();
        }
        else {
            throw new RuntimeException("connection does not exist");
        }
    }

    @Override
    public PlayerConnection createPlayerConnection(PlayerConnection playerConnection) {
        return playerConnectionRepository.save(playerConnection);
    }

    @Override
    public PlayerConnection updatePlayerConnection(PlayerConnection playerConnection) {
        if (playerConnectionRepository.findById(playerConnection.getPlayerConnectionId()).isPresent()) {
            return playerConnectionRepository.save(playerConnection);
        }
        throw new RuntimeException("connection does not exist");

    }

    @Override
    public void deletePlayerConnection(long playerConnectionId) {
        if (playerConnectionRepository.existsById(playerConnectionId)) {
            playerConnectionRepository.deleteById(playerConnectionId);
        }
    }
}

