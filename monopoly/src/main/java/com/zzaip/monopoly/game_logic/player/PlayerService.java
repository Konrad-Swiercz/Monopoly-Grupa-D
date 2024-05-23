package com.zzaip.monopoly.game_logic.player;

import java.util.List;

public interface PlayerService {
    int RollDice();

    List<Player> getPlayers();
    Player createPlayer(Player player);

    Player updatePlayer(Player player);

    Player findById(int id);
    Player findByName(String name);
    void movePlayer(String playerName, int position);
    void modifyBalance(String playerName, float balanceChange);
    boolean moveToJail(String playerName, int jailTurns);
    void updatePlayerIfLost(Player player);


}