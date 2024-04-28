package com.zzaip.monopoly.communication.connection;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlayerConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long playerConnectionId;

    private long playerId;

    private String playerName;

    @Column(unique = true)
    private String playerURL;

    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlayerConnection that = (PlayerConnection) o;
        return Objects.equals(playerConnectionId, that.playerConnectionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
