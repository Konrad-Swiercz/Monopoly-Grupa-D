package com.zzaip.monopoly.communication.connection;

import com.zzaip.monopoly.communication.dto.PlayerConnectionDTO;
import com.zzaip.monopoly.communication.game_room.GameRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public PlayerConnectionDTO convertToDTO(PlayerConnection playerConnection) {
        return new PlayerConnectionDTO(
                playerConnection.getPlayerName(),
                playerConnection.getPlayerURL(),
                playerConnection.isActive()
        );

    }

    @Override
    public PlayerConnection convertToPlayerConnection(PlayerConnectionDTO playerConnectionDTO) {
        return PlayerConnection.builder()
                .playerName(playerConnectionDTO.getPlayerName())
                .playerURL(playerConnectionDTO.getPlayerURL())
                .isActive(playerConnectionDTO.isActive())
                .build();
    }

    @Override
    public List<PlayerConnection> createPlayerConnectionsFromDTOs(List<PlayerConnectionDTO> playerConnectionDTOS) {
        List<PlayerConnection> playerConnections = new ArrayList<>();
        for (PlayerConnectionDTO playerConnectionDTO: playerConnectionDTOS) {
            PlayerConnection playerConnection = convertToPlayerConnection(playerConnectionDTO);
            playerConnection = createPlayerConnection(playerConnection);
            playerConnections.add(playerConnection);
        }
        return playerConnections;
    }

}

