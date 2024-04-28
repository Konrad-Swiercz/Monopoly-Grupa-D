package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class GameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long gameRoomId;

    private String roomName;

    private boolean isOwner;

    private boolean isActive;

    private int playersLimit;

    @OneToMany
    @JoinColumn(
            name = "connection_id",
            referencedColumnName = "playerConnectionId"
    )
    private List<PlayerConnection> connectedPlayers;
}

