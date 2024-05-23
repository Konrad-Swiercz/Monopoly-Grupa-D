package com.zzaip.monopoly.dto;

import lombok.Data;

@Data
public class PlayerDTO {
    private String playerName;
    private int playerPosition;
    private float playerBalance;
    private int jailTurns;
    private boolean hasLost;
}