package com.zzaip.monopoly.game_logic.player;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue
    private long playerId;
    @Column(unique = true)
    private String playerName;
    private int playerPosition;
    private float playerBalance;
    private int jailTurns;
    private boolean hasLost;

    public Player(String playerName){
        this.playerName = playerName;
    }
}
