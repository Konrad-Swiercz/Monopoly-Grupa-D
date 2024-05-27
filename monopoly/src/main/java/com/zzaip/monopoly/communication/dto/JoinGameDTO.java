package com.zzaip.monopoly.communication.dto;

import com.zzaip.monopoly.dto.GameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinGameDTO {
    private GameRoomDTO gameRoom;
    private GameDTO game;
}
