package com.zzaip.monopoly.service;

import com.zzaip.monopoly.model.Player;
import org.springframework.data.jpa.repository.Query;

public interface PlayerService {
    int RollDice();

    Player createPlayer(Player player);

    Player updatePlayer(Player player);

    Player findById(int id);
    void movePlayer(String playerName, int position);
    void modifyBalance(String playerName, float balanceChange);
    boolean moveToJail(String playerName, int jailTurns);
    void playerLost(String playerName);

}