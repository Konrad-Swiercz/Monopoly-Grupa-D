package com.zzaip.monopoly.game_logic.player;

public interface PlayerParser {
    /**
     * Create a new Player with given name with default initial wallet
     * based on the configuration
     * @param playerName name of the player that is being created
     * @return a Player object with given name and initial values taken from config file
     */
    Player parsePlayerFromConfig(String playerName);
}
