package com.zzaip.monopoly.communication.game_room;

import com.zzaip.monopoly.communication.connection.PlayerConnection;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
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
    @ToString.Exclude
    private List<PlayerConnection> connectedPlayers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GameRoom gameRoom = (GameRoom) o;
        return Objects.equals(gameRoomId, gameRoom.gameRoomId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

