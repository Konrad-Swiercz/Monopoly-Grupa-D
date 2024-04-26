package com.zzaip.monopoly.communication.connection;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PlayerConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long playerConnectionId;

    private int playerId;

    private String playerName;

    @Column(unique = true)
    private String playerURL;

    private boolean isActive;
}
