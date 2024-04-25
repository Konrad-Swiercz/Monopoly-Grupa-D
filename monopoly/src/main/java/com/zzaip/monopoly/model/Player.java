package com.zzaip.monopoly.model;

import jakarta.persistence.*;

@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue
    @Column(name="player_id")
    private int player_id;

    @Column(name="player_name")
    private String player_name;

    @Column(name="player_position")
    private int player_position;

    @Column(name="player_balance")
    private float player_balance;

    @Column(name="jail_turns")
    private int jail_turns;

    @Column(name="has_lost")
    private boolean has_lost;

    public Player(String player_name, int player_position, float player_balance, int jail_turns, boolean has_lost) {
        this.player_name = player_name;
        this.player_position = player_position;
        this.player_balance = player_balance;
        this.jail_turns = jail_turns;
        this.has_lost = has_lost;
    }

    public Player(String player_name, float player_balance) {
        this.player_name = player_name;
        this.player_balance = player_balance;
        this.player_position = 0;
        this.jail_turns = 0;
        this.has_lost = false;
    }

    public Player(){

    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getPlayer_position() {
        return player_position;
    }

    public void setPlayer_position(int player_position) {
        this.player_position = player_position;
    }

    public float getPlayer_balance() {
        return player_balance;
    }

    public void setPlayer_balance(float player_balance) {
        this.player_balance = player_balance;
    }

    public int getJail_turns() {
        return jail_turns;
    }

    public void setJail_turns(int jail_turns) {
        this.jail_turns = jail_turns;
    }

    public boolean isHas_lost() {
        return has_lost;
    }

    public void setHas_lost(boolean has_lost) {
        this.has_lost = has_lost;
    }

    @Override
    public String toString() {
        return "Player{" +
                "player_id=" + player_id +
                ", player_name='" + player_name + '\'' +
                ", player_position=" + player_position +
                ", player_balance=" + player_balance +
                ", jail_turns=" + jail_turns +
                ", has_lost=" + has_lost +
                '}';
    }
}
