package com.zzaip.monopoly.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GameRoomDTO {
    private String roomName;
    private boolean isActive;
    private int playersLimit;
    private List<PlayerConnectionDTO> connectedPlayers;
}
