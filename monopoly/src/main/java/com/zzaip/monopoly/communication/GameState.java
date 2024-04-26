package com.zzaip.monopoly.communication;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
public class GameState implements Serializable {

    private int gameId;
    private String status;
    private int roundCount;
    private List<String> players;
    private List<String> playersQueue;
    private HashMap<String, Boolean> playersIsActive;
    private String currentPlayer;
    private HashMap<String, Integer> positions;
    private HashMap<String, Float> wallets;
    private HashMap<String, Integer> daysInJail;
    private HashMap<Integer, String> propertyOwnership;
    private HashMap<Integer, Integer> propertyHouses;
}
