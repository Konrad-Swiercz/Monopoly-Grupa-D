package com.zzaip.monopoly;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class PlayerList {
    @Id
    @GeneratedValue
    private int playerListId;
    private List<String> playersList;


    public PlayerList(List<String> playersList) {
        this.playersList = playersList;
    }

    public List<String> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<String> playersList) {
        this.playersList = playersList;
    }

    @Override
    public String toString() {
        return "PlayerList{" +
                "playersList=" + playersList +
                '}';
    }
}
