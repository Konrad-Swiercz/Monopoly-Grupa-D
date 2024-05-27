package com.zzaip.monopoly.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameDTO {
    private int gameId;
    private String status;
    private int roundCount;
    private List<PlayerDTO> players;
    private List<String> playersQueue;
    private String currentPlayerName;
    private String winnerPlayerName;
    private List<PropertyFieldDTO> properties;
}