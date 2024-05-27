package com.zzaip.monopoly.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerConnectionDTO {
    private String playerName;
    private String playerURL;
    private boolean isActive;
}
