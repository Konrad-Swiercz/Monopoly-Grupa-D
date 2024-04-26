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
    private int gameRoomId;

    private String roomName;

    private int maxPlayers;

    @OneToMany
    @JoinColumn(
            name = "connection_id",
            referencedColumnName = "playerConnectionId"
    )
    private List<PlayerConnection> connectedPlayers;
}

